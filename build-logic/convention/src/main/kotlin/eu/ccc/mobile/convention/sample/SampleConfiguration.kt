package eu.ccc.mobile.convention.sample

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import java.io.File

internal fun ApplicationExtension.applySampleCommons(project: Project) {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        named("release").configure {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }

        named("debug").configure {
            applicationIdSuffix = ".debug"
        }
    }

    signingConfigs {
        named("debug").configure {
            keyAlias = "android-debug"
            keyPassword = "android"
            storeFile = File("${project.rootDir}/keystore/debug.keystore")
            storePassword = "android"
        }
    }
}