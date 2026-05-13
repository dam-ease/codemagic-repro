plugins {
    alias(deps.plugins.library)
    alias(deps.plugins.cccAndroid)
    alias(deps.plugins.ksp)
}

android {
    namespace = "eu.ccc.mobile.mobileservices.huawei"
}

dependencies {
    implementation(deps.huaweiServices.core)
    implementation(deps.huaweiServices.location)
    implementation(deps.huaweiServices.push)
    implementation(deps.kotlin.coroutines.core)
}