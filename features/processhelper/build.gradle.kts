plugins {
    alias(deps.plugins.library)
    alias(deps.plugins.cccAndroid)
    alias(deps.plugins.cccDepsAndroidPresentation)
    alias(deps.plugins.ksp)
}

android {
    namespace = "eu.ccc.mobile.features.processhelper"
}

dependencies {
    ksp(deps.hilt.compiler)
    implementation(deps.hilt.runtime)
}