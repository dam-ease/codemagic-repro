dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("deps") {
            from(files("../gradle/dependencies.toml"))
        }
    }
}

rootProject.name = "build-logic"

include(":convention")

include(":utils")
include(":config")