plugins {
    alias(deps.plugins.cccMultiplatform)
    alias(deps.plugins.cccMultiplatformKoin)
    alias(deps.plugins.kotlinSerialization)
}

kotlin {
    android {
        namespace = "eu.ccc.mobile.shared.config"
    }
    sourceSets {
        commonMain.dependencies {
        }
        androidMain.dependencies {
            implementation(deps.firebase.analytics)
            implementation(deps.firebase.config)
            implementation(deps.kotlin.serialization)
            implementation(deps.laboratory.sharedPreferences)
            implementation(deps.timber)
            implementation(project.dependencies.platform(deps.firebase.bom))

            implementation(project(":shared:platform"))
        }
    }
}
