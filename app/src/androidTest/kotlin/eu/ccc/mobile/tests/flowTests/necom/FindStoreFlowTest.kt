package eu.ccc.mobile.tests.flowTests.necom

import androidx.compose.ui.test.junit4.createComposeRule
import arrow.core.Either
import eu.ccc.mobile.backend.fake.fixtures.HOME_JSON
import eu.ccc.mobile.backend.fake.fixtures.TestStore
import eu.ccc.mobile.backend.fake.mocks.necom.ConfigurationMocks
import eu.ccc.mobile.backend.fake.mocks.necom.ProductMocks
import eu.ccc.mobile.backend.fake.mockwebserver.MockWebServer
import eu.ccc.mobile.backend.fake.mockwebserver.enqueue
import eu.ccc.mobile.backend.fake.mockwebserver.putDefaultResponses
import eu.ccc.mobile.data.synerise.fake.homecontent.FakeHomeContentRepository
import eu.ccc.mobile.di.testAppComponent
import eu.ccc.mobile.domain.model.synerise.DocumentJson
import eu.ccc.mobile.robots.common.availabilitySizeRobot
import eu.ccc.mobile.robots.common.homeRobot
import eu.ccc.mobile.robots.common.mainRobot
import eu.ccc.mobile.robots.common.storeRobot
import eu.ccc.mobile.robots.common.yourStoreRobot
import eu.ccc.mobile.robots.necom.Availability
import eu.ccc.mobile.robots.necom.categoriesRobot
import eu.ccc.mobile.robots.necom.productListRobot
import eu.ccc.mobile.robots.necom.productRobot
import eu.ccc.mobile.rules.ApplicationTestRule
import eu.ccc.mobile.rules.LocationRule
import eu.ccc.mobile.rules.RemoteConfigRule
import eu.ccc.mobile.shared.config.remoteconfig.RemoteConfigKey
import eu.ccc.mobile.shared.domain.model.location.MapCoordinates
import eu.ccc.mobile.shared.utils.clock.KotlinDateTimeMachine
import eu.ccc.mobile.uiautomator.interactions.clickBack
import eu.ccc.mobile.util.launchScenario
import kotlinx.datetime.LocalDateTime
import org.junit.Rule
import org.junit.Test

class FindStoreFlowTest {

    private val server = MockWebServer()
    @get:Rule val applicationTestRule = ApplicationTestRule(server)
    @get:Rule val composeTestRule = createComposeRule()
    @get:Rule val locationRule = LocationRule(MapCoordinates(latitude = 51.9189046, longitude = 19.1343786))
    @get:Rule val remoteConfigRule = RemoteConfigRule().apply {
        setBoolean(RemoteConfigKey.Necom.IS_NECOM_ENABLED, true)
    }

    private val fakeHomeContentRepository: FakeHomeContentRepository
        get() = testAppComponent.fakeHomeContentRepository

    private val clock: KotlinDateTimeMachine
        get() = testAppComponent.kotlinDateTimeMachine

    private val firstStore = TestStore(
        name = "CCC 2524 Aleksandrów Łódzki",
        street = "Konstantynowska 5-7",
        fullAddress = "Konstantynowska 5-7, 95070 Aleksandrów Łódzki",
        distance = "17,3 km",
        phoneNumber = "887472614"
    )

    private val secondStore = TestStore(
        name = "CCC 2330 Wrocław Galeria Wroclavia",
        street = "Sucha 1",
        fullAddress = "Sucha 1, 50086 Wrocław",
        distance = "171,7 km",
        phoneNumber = "887472687",
    )

    @Test
    fun checkSizesInFindStore() = with(composeTestRule) {
        clock.timeTravelTo(LocalDateTime(2020, 3, 27, 0, 0))
        fakeHomeContentRepository.overrideNextResult(Either.Right(DocumentJson(HOME_JSON)))
        remoteConfigRule.setBoolean(RemoteConfigKey.Features.YOUR_STORE, true)

        launchScenario()
        chooseYourStoreFromHome(position = 1, store = secondStore)

        server.putDefaultResponses(
            ConfigurationMocks.GetMenu.Default,
            ProductMocks.GetVirtualListingFilters.Default,
            ProductMocks.GetVirtualListingSimple.Default(),
            ProductMocks.Product.WithStoresStock(),
        )
        mainRobot { clickCategoriesTab() }
        categoriesRobot { openCategory("Nowości") }
        productListRobot { clickProductAtPositionOnListing(position = 0) }

        productRobot {
            assertCheckStoreAvailabilityItemDisplayed()
            clickCheckStoreAvailability()
        }
        availabilitySizeRobot {
            assertUnavailableSizeInfoComponentDisplayed(secondStore)
            assertSearchStoreDisplayed()
            assertSizesAvailabilityOnFindStoreDisplayed()
            assertStoreListDisplayed()
            selectSize(size = "36")
            assertSizeInStoreAvailable(
                size = "36",
                store = firstStore,
                storePosition = 0,
                productAvailable = Availability.Few
            )
            assertSizeInStoreAvailable(
                size = "36",
                store = secondStore,
                storePosition = 2,
                productAvailable = Availability.OutOfStock
            )
            clickStore(position = 0)
        }
        storeRobot {
            assertStoreNameVisible(store = firstStore.name)
            clickBack()
        }
        availabilitySizeRobot {
            selectSize(size = "39")
            assertSizeInStoreAvailable(
                size = "39",
                store = firstStore,
                storePosition = 0,
                productAvailable = Availability.One
            )
            assertSizeInStoreAvailable(
                size = "39",
                store = secondStore,
                storePosition = 2,
                productAvailable = Availability.OutOfStock
            )
            clickStore(position = 2)
        }
        storeRobot {
            assertStoreNameVisible(store = secondStore.name)
            clickBack()
        }
        availabilitySizeRobot { assertStoreListDisplayed() }
    }

    private fun chooseYourStoreFromHome(position: Int, store: TestStore) = with(composeTestRule) {
        homeRobot {
            server.enqueue(ConfigurationMocks.GetShops)
            clickYourStoreComponent()
        }
        yourStoreRobot {
            assertSearchStoreDisplayed()
            clickStore(position = position)
            clickSetYourStoreButton()
        }
        homeRobot { assertYourStoreComponentDisplayed(storeName = store.name) }
    }
}
