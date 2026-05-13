plugins {
    alias(deps.plugins.library)
    alias(deps.plugins.cccAndroid)
    alias(deps.plugins.ksp)
}

android {
    namespace = "eu.ccc.mobile.services.firebase"
}

dependencies {
    ksp(deps.hilt.compiler)
    implementation(deps.firebase.analytics)
    implementation(deps.firebase.perf)
    implementation(deps.hilt.runtime)
    implementation(deps.timber)
    implementation(platform(deps.firebase.bom))

    testImplementation(deps.robolectric)
}