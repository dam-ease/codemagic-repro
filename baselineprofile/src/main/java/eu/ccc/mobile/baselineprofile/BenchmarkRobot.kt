package eu.ccc.mobile.baselineprofile

import android.app.Instrumentation
import android.content.Context
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import eu.ccc.mobile.translations.R as TranslationsR

private const val DEFAULT_TIMEOUT = 20_000L

private val instrumentation: Instrumentation
    get() = InstrumentationRegistry.getInstrumentation()

private val targetContext: Context
    get() = instrumentation.targetContext

fun MacrobenchmarkScope.waitForMarketSelectionLoaded() =
    device.wait(Until.hasObject(By.res("confirmMarketSelectionButton")), DEFAULT_TIMEOUT)

fun MacrobenchmarkScope.confirmMarketSelection() {
    device.findObject(By.res("confirmMarketSelectionButton")).click()
    device.waitForIdle()
}

fun MacrobenchmarkScope.isLegalScreenVisible() = device.wait(
    Until.hasObject(By.res("acceptAllLegalConsentsButton")),
    DEFAULT_TIMEOUT,
)

fun MacrobenchmarkScope.confirmLegalConsents() {
    device.findObject(By.res("acceptAllLegalConsentsButton")).click()
    device.waitForIdle()
}

fun MacrobenchmarkScope.isLoginScreenVisible() =
    device.wait(Until.hasObject(By.res("skipLoginButton")), DEFAULT_TIMEOUT)

fun MacrobenchmarkScope.skipLogIn() {
    device.findObject(By.res("skipLoginButton")).click()
    device.waitForIdle()
}

fun MacrobenchmarkScope.isPreferenceChooserVisible() =
    device.wait(Until.hasObject(By.res("preferenceChooserTitle")), DEFAULT_TIMEOUT)

fun MacrobenchmarkScope.selectWomanPreference() {
    device.findObject(By.res("preferencesCategoryList_Woman")).click()
    device.waitForIdle()
    device.findObject(By.res("saveButton")).click()
    device.waitForIdle()
}

fun MacrobenchmarkScope.isPermissionForNotificationsDialogVisible() =
    device.wait(
        Until.hasObject(By.text(targetContext.getString(TranslationsR.string.notification_dialog_message))),
        DEFAULT_TIMEOUT
    )

fun MacrobenchmarkScope.confirmPermissionForNotifications() {
    device.findObject(By.text(targetContext.getString(TranslationsR.string.yes))).click()
    device.waitForIdle()
}

fun MacrobenchmarkScope.waitForHomeLoaded() {
    device.wait(Until.hasObject(By.res("welcomeMessage")), DEFAULT_TIMEOUT)
}

fun MacrobenchmarkScope.completeOnboardingIfNeeded() {
    val hasMarketSelection = waitForMarketSelectionLoaded()
    if (hasMarketSelection.not()) {
        waitForHomeLoaded()
        return
    }
    confirmMarketSelection()

    if (isLegalScreenVisible()) {
        confirmLegalConsents()
    }

    if (isLoginScreenVisible()) {
        skipLogIn()
    }

    if (isPreferenceChooserVisible()) {
        selectWomanPreference()
    }

    if (isPermissionForNotificationsDialogVisible()) {
        confirmPermissionForNotifications()
    }

    waitForHomeLoaded()
}
