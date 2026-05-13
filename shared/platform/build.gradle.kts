plugins {
    alias(deps.plugins.cccMultiplatform)
    alias(deps.plugins.cccMultiplatformKoin)
}

kotlin {
    android {
        namespace = "eu.ccc.mobile.shared.platform"
    }
    sourceSets {
        commonMain.dependencies {
            implementation(deps.androidX.datastore.preferences)
        }
        androidMain.dependencies {
            api(deps.androidX.datastore.preferences.core)
            implementation(deps.androidX.ktxCore)

            implementation(project(":translations"))
        }
    }
}