pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://pkgs.dev.azure.com/Synerise/AndroidSDK/_packaging/prod/maven/v1") }
        maven { setUrl("https://maven.lokalise.com") }
        maven { setUrl("https://developer.huawei.com/repo/") }
        maven { setUrl("https://payu.jfrog.io/payu/mobile-sdk-gradle-local") }
        maven { setUrl("https://gitlab.com/api/v4/projects/27836447/packages/maven/") }
        maven { setUrl("https://www.jitpack.io") } // jitpack should remain last in order according to its documentation
    }

    versionCatalogs {
        create("deps") {
            from(files("$rootDir/gradle/dependencies.toml"))
        }
    }
}

rootProject.name = "ccc-android"

include(":app")
include(":baselineprofile")

include(":translations")
include(":resources")

include(":shared:config")
include(":shared:platform")

include(":features:firebase")
include(":features:mobileServices:google")
include(":features:mobileServices:huawei")
include(":features:processhelper")

include(":utils:test:androidTests")
