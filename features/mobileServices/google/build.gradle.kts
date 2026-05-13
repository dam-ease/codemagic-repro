plugins {
    alias(deps.plugins.library)
    alias(deps.plugins.cccAndroid)
    alias(deps.plugins.ksp)
}

android {
    namespace = "eu.ccc.mobile.mobileservices.google"
}

dependencies {
    implementation(deps.firebase.messaging)
    implementation(deps.kotlin.coroutines.core)
    implementation(deps.kotlin.coroutines.playServices)
    implementation(deps.playServices.location)
    implementation(platform(deps.firebase.bom))

    implementation(project(":shared:config"))
}