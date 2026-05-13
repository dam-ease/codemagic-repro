import com.android.build.api.variant.impl.VariantOutputImpl
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import com.google.firebase.perf.plugin.FirebasePerfExtension
import eu.ccc.mobile.config.BuildConfig
import eu.ccc.mobile.config.Constants.Keys
import eu.ccc.mobile.config.Constants.Urls
import org.jetbrains.kotlin.konan.properties.Properties
import ru.cian.huawei.publish.BuildFormat
import ru.cian.huawei.publish.DeployType
import java.io.FileInputStream

plugins {
    alias(deps.plugins.application)
    alias(deps.plugins.cccAndroid)
    alias(deps.plugins.cccAndroidKoin)
    alias(deps.plugins.cccCompose)
    alias(deps.plugins.cccDepsKotlin)
    alias(deps.plugins.cccDepsAndroidData)
    alias(deps.plugins.cccDepsAndroidPresentation)
    alias(deps.plugins.ksp)
    alias(deps.plugins.googleServices)
    alias(deps.plugins.agconnect)
    alias(deps.plugins.huaweiAppGalleryPublisher)
    alias(deps.plugins.crashlytics)
    alias(deps.plugins.firebasePerf)
    alias(deps.plugins.easylauncher)
    alias(deps.plugins.hilt)
    alias(deps.plugins.cccFladle)
    alias(deps.plugins.baselineprofile)
}

fun getLocalProperty(key: String): String? {
    val localProperties = Properties()
    val file = rootProject.file("local.properties")
    if (file.exists().not()) return null

    return FileInputStream(file).use {
        localProperties.load(it)
        localProperties.getProperty(key)
    }
}

agcp {
    manifest = false
}

huaweiPublish {
    instances {
        create("release") {
            credentialsPath = "$rootDir/huawei_api_config.json"
            deployType = DeployType.DRAFT
            buildFormat = BuildFormat.APK
        }
    }
}

android {
    namespace = "eu.ccc.mobile"
    testNamespace = "eu.ccc.mobile.test"

    buildFeatures.buildConfig = true

    bundle {
        language {
            // We use Lokalise for changing app language based on market selection so we need to bundle all languages.
            enableSplit = false
        }
    }

    configurations.all {
        exclude(module = "bcprov-jdk18on")
        exclude(module = "bcprov-jdk15to18")
        exclude(module = "kotlin-android-extensions-runtime")
    }

    defaultConfig {
        applicationId = "eu.ccc.mobile"

        versionCode = 1
        versionName = "1.0.0"

        testBuildType = "fake"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        testInstrumentationRunnerArguments["listener"] =
            "eu.ccc.mobile.listener.ToastingRunListener"

        buildConfigField("Boolean", "IS_RELEASE", "false")
        buildConfigField("Boolean", "IS_FAKE", "false")
        buildConfigField("String", "APPSFLYER_API_KEY", Keys.APPSFLYER)
        buildConfigField("String", "PAYPO_PRODUCTION_BASE_URL", Urls.PAYPO_PROD)
        buildConfigField("String", "PAYPO_SANDBOX_BASE_URL", Urls.PAYPO_SANDBOX)
        buildConfigField("String", "PAYPO_SANDBOX_CLIENT_ID", Keys.PAYPO_SANDBOX_CLIENT_ID)
        buildConfigField("String", "PAYPO_SANDBOX_CLIENT_SECRET", Keys.PAYPO_SANDBOX_CLIENT_SECRET)
        buildConfigField(
            "String",
            "GOOGLE_PAY_SANDBOX_MERCHANT_NAME",
            Keys.GOOGLE_PAY_SANDBOX_MERCHANT_NAME
        )
        buildConfigField(
            "String",
            "GOOGLE_PAY_SANDBOX_MERCHANT_POS_ID",
            Keys.GOOGLE_PAY_SANDBOX_MERCHANT_POS_ID
        )
        buildConfigField(
            "String",
            "GOOGLE_PAY_PRODUCTION_MERCHANT_NAME",
            Keys.GOOGLE_PAY_PROD_MERCHANT_NAME
        )
        buildConfigField(
            "String",
            "GOOGLE_PAY_PRODUCTION_MERCHANT_POS_ID",
            Keys.GOOGLE_PAY_PROD_MERCHANT_POS_ID
        )
        buildConfigField("String", "BUILD_NUMBER", BuildConfig.NUMBER)
    }

    // The defaultConfig values above are fixed, so your incremental builds don"t
    // need to rebuild the manifest (and therefore the whole app, slowing build times).
    // But for release builds, it"s okay. So the following script iterates through
    // all the known variants, finds those that are "release" build types, and
    // changes those properties to something dynamic.
    androidComponents.onVariants { variant ->
        variant.outputs.forEach { output ->
            (output as VariantOutputImpl).let {
                it.versionCode = 1
                it.versionName = "999.999.999"
            }
        }
    }

    signingConfigs {
        named("debug").configure {
            keyAlias = "android-debug"
            keyPassword = "android"
            storeFile = file("$rootDir/keystore/debug.keystore")
            storePassword = "android"
        }

        register("release") {
            if (System.getenv("CM_KEYSTORE_PATH") != null) {
                storeFile = file(System.getenv("CM_KEYSTORE_PATH"))
                storePassword = System.getenv("CM_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("CM_KEY_ALIAS")
                keyPassword = System.getenv("CM_KEY_PASSWORD")
            } else {
                initWith(getByName("debug"))
            }
        }
    }

    buildTypes {
        named("release").configure {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "$projectDir/proguardRules/proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")

            buildConfigField("Boolean", "IS_RELEASE", "true")
        }

        register("qa") {
            isDebuggable = true // required by LeakCanary
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "$projectDir/proguardRules/proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("debug")

            versionNameSuffix = "-qa"
            applicationIdSuffix = ".qa"

            matchingFallbacks.add("release")
        }

        named("debug").configure {
            isMinifyEnabled = false

            signingConfig = signingConfigs.getByName("debug")

            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"

            (this as ExtensionAware).configure<CrashlyticsExtension> {
                // If you don"t need crash reporting for your debug build,
                // you can speed up your build by disabling mapping file uploading.
                mappingFileUploadEnabled = false
            }

            (this as ExtensionAware).configure<FirebasePerfExtension> {
                // Set this flag to "false" to disable @AddTrace annotation processing and
                // automatic monitoring of HTTP/S network requests
                // for a specific build variant at compile time.
                setInstrumentationEnabled(false)
            }
        }

        register("fake") {
            isDebuggable = true
            isMinifyEnabled = false

            signingConfig = signingConfigs.getByName("debug")

            applicationIdSuffix = ".fake"
            versionNameSuffix = "-fake"

            buildConfigField("Boolean", "IS_FAKE", "true")

            matchingFallbacks.add("debug")

            (this as ExtensionAware).configure<CrashlyticsExtension> {
                // If you don"t need crash reporting for your debug build,
                // you can speed up your build by disabling mapping file uploading.
                mappingFileUploadEnabled = false
            }
        }
    }

    sourceSets {
        getByName("main").kotlin.directories.add("src/main/kotlin")

        getByName("qa").apply {
            kotlin.directories.add("src/nonRelease/kotlin")
            kotlin.directories.add("src/nonFake/kotlin")
        }

        getByName("debug").apply {
            kotlin.directories.add("src/nonRelease/kotlin")
            kotlin.directories.add("src/nonFake/kotlin")
        }

        getByName("fake").apply {
            kotlin.directories.add("src/nonRelease/kotlin")
            res.directories.add("src/debug/res")
        }

        getByName("release").apply {
            kotlin.directories.add("src/nonFake/kotlin")
        }
    }

    testOptions {
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

easylauncher {
    buildTypes {
        register("release") {
            enable(false)
        }
        register("qa") { filters(chromeLike()) }
        register("debug") { filters(chromeLike()) }
        register("fake") { filters(chromeLike()) }
    }
}

dependencies {
    implementation(deps.androidX.profileinstaller)
    "baselineProfile"(project(":baselineprofile"))
    ksp(deps.hilt.compiler)
    kspAndroidTest(deps.hilt.compiler)

    implementation((deps.hilt.runtime))
    implementation(deps.accompanist.permissions)
    implementation(deps.adapterDelegates)
    implementation(deps.androidX.activity)
    implementation(deps.androidX.annotation)
    implementation(deps.androidX.appCompat)
    implementation(deps.androidX.archCoreTesting)
    implementation(deps.androidX.benchmark.macro.junit4)
    implementation(deps.androidX.camera.camera2)
    implementation(deps.androidX.camera.cameraView)
    implementation(deps.androidX.camera.core)
    implementation(deps.androidX.camera.lifecycle)
    implementation(deps.androidX.camera.mlkit.vision)
    implementation(deps.androidX.cardView)
    implementation(deps.androidX.compose.activity)
    implementation(deps.androidX.compose.animation)
    implementation(deps.androidX.compose.constraintLayout)
    implementation(deps.androidX.compose.foundation)
    implementation(deps.androidX.compose.icons)
    implementation(deps.androidX.compose.material)
    implementation(deps.androidX.compose.material3)
    implementation(deps.androidX.compose.navigation)
    implementation(deps.androidX.compose.preview)
    implementation(deps.androidX.compose.ui)
    implementation(deps.androidX.constraintLayout)
    implementation(deps.androidX.coordinatorLayout)
    implementation(deps.androidX.customTabs)
    implementation(deps.androidX.fragment)
    implementation(deps.androidX.gridLayout)
    implementation(deps.androidX.interpolator)
    implementation(deps.androidX.ktxCore)
    implementation(deps.androidX.lifecycle.commonJava8)
    implementation(deps.androidX.lifecycle.liveData)
    implementation(deps.androidX.lifecycle.process)
    implementation(deps.androidX.lifecycle.viewModel)
    implementation(deps.androidX.paging)
    implementation(deps.androidX.preference)
    implementation(deps.androidX.profileinstaller)
    implementation(deps.androidX.recyclerView)
    implementation(deps.androidX.startup)
    implementation(deps.androidX.test.espresso)
    implementation(deps.androidX.test.extJunit)
    implementation(deps.androidX.test.uiAutomator)
    implementation(deps.androidX.viewbinding)
    implementation(deps.appsFlyer)
    implementation(deps.arrow.core)
    implementation(deps.arrow.coroutines)
    implementation(deps.balloon)
    implementation(deps.bundles.test)
    implementation(deps.bundles.test.async)
    implementation(deps.bundles.test.multiplatform)
    implementation(deps.byteunits)
    implementation(deps.clarity)
    implementation(deps.coil.compose)
    implementation(deps.coil.core)
    implementation(deps.coil.network.okhttp)
    implementation(deps.coil.svg)
    implementation(deps.coil.test)
    implementation(deps.deviceNames)
    implementation(deps.exoPlayer)
    implementation(deps.facebook.shimmer)
    implementation(deps.firebase.analytics)
    implementation(deps.firebase.crashlytics)
    implementation(deps.firebase.messaging)
    implementation(deps.firebase.perf)
    implementation(deps.huaweiServices.core)
    implementation(deps.huaweiServices.installReferrer)
    implementation(deps.inputMask)
    implementation(deps.installReferrer)
    implementation(deps.kermit.koin)
    implementation(deps.kohii.core)
    implementation(deps.kohii.exoPlayer)
    implementation(deps.konfetti)
    implementation(deps.konfetti.compose)
    implementation(deps.kotlin.coroutines.android)
    implementation(deps.kotlin.coroutines.core)
    implementation(deps.kotlin.coroutines.test)
    implementation(deps.kotlin.datetime)
    implementation(deps.kotlin.reflect)
    implementation(deps.ktor.core)
    implementation(deps.laboratory.core)
    implementation(deps.laboratory.hyperionPlugin)
    implementation(deps.lokalise)
    implementation(deps.lottieCompose)
    implementation(deps.material)
    implementation(deps.mlkit.barcode.scanning)
    implementation(deps.moshi.adapters)
    implementation(deps.moshi.core)
    implementation(deps.moshi.kotlin)
    implementation(deps.moshix.sealed)
    implementation(deps.moshix.sealedReflect)
    implementation(deps.okHttp.client)
    implementation(deps.okHttp.loggingInterceptor)
    implementation(deps.okHttp.mockWebServer)
    implementation(deps.okHttp.tls)
    implementation(deps.openStreetMaps)
    implementation(deps.openStreetMapsBonusPack)
    implementation(deps.payPo)
    implementation(deps.payu.googlePayAdapter)
    implementation(deps.payu.googlePayModule)
    implementation(deps.payu.webView)
    implementation(deps.photoView)
    implementation(deps.playServices.mapsUtils)
    implementation(deps.playServices.review)
    implementation(deps.plumber)
    implementation(deps.retrofit.core)
    implementation(deps.retrofit.moshiConverter)
    implementation(deps.retrofit.scalars)
    implementation(deps.synerise)
    implementation(deps.timber)
    implementation(deps.unearthed)
    implementation(deps.zoomable)
    implementation(deps.zxing.core)
    implementation(platform(deps.androidX.compose.bom))
    implementation(platform(deps.arrow.bom))
    implementation(platform(deps.firebase.bom))
    implementation(platform(deps.okHttp.bom))

    compileOnly(deps.javaXInject)

    implementation(project(":features:processhelper"))
    implementation(project(":shared:config"))
    implementation(project(":shared:platform"))

    debugImplementation(deps.androidX.compose.uiTooling)
    debugImplementation(deps.foqa)
    debugImplementation(deps.leakCanary)
    debugImplementation(project(":features:firebase"))

    releaseImplementation(project(":features:firebase"))

    "qaImplementation"(deps.foqa)
    "qaImplementation"(deps.leakCanary)
    "qaImplementation"(project(":features:firebase"))

    "fakeImplementation"(deps.androidX.compose.uiTooling)
    "fakeImplementation"(deps.foqa)
    "fakeImplementation"(deps.leakCanary)
    "fakeImplementation"(project(":utils:test:androidTests"))

    testImplementation(deps.androidX.archCoreTesting)
    testImplementation(deps.androidX.lifecycle.testing)
    testImplementation(deps.kotlin.reflect)
    testImplementation(deps.okHttp.client)
    testImplementation(deps.okHttp.mockWebServer)
    testImplementation(deps.retrofit.mock)
    testImplementation(deps.robolectric)
    testImplementation(kotlin("test"))
    testImplementation(platform(deps.okHttp.bom))

    androidTestImplementation(deps.androidX.compose.uiTestJunit)
    androidTestImplementation(deps.hamcrest)
    androidTestImplementation(deps.okHttp.mockWebServer)
    androidTestImplementation(project(":utils:test:androidTests"))
    androidTestUtil(deps.androidX.test.orchestrator)
}
