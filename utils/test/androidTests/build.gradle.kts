plugins {
    alias(deps.plugins.library)
    alias(deps.plugins.cccAndroid)
    alias(deps.plugins.ksp)
    alias(deps.plugins.hilt)
}

android {
    namespace = "eu.ccc.mobile.test.androidtests"

    defaultConfig {
        testInstrumentationRunner = "eu.ccc.mobile.test.androidtests.fragment.AppCompatFragmentScenarioTestRunner"
    }
}

dependencies {
    ksp(deps.hilt.compiler)
    api(deps.androidX.test.core)
    api(deps.androidX.test.espresso)
    api(deps.androidX.test.espresso.contrib) {
        exclude(module = "protobuf-lite")
    }
    api(deps.androidX.test.rules)
    api(deps.androidX.test.runner)
    api(deps.androidX.test.uiAutomator)
    api(deps.bundles.test)
    api(deps.okHttp.client)
    api(platform(deps.okHttp.bom))
    implementation(deps.androidX.compose.uiTest)
    implementation(deps.androidX.compose.uiTestJunit)
    implementation(deps.androidX.fragmentTesting)
    implementation(deps.androidX.recyclerView)
    implementation(deps.hilt.runtime)
    implementation(platform(deps.androidX.compose.bom))
    debugImplementation(deps.androidX.compose.testManifest)
}