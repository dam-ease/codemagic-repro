import org.gradle.api.internal.file.DefaultConfigurableFilePermissions

plugins {
    alias(deps.plugins.dependencyAnalysis)
    alias(deps.plugins.ksp) apply false
    alias(deps.plugins.composeCompiler) apply false
    alias(deps.plugins.application) apply false
    alias(deps.plugins.library) apply false
    alias(deps.plugins.kotlinSerialization) apply false
    alias(deps.plugins.laboratory) apply false
    alias(deps.plugins.hilt) apply false
    alias(deps.plugins.paparazzi) apply false
    alias(deps.plugins.cccDetekt)
    alias(deps.plugins.test) apply false
    alias(deps.plugins.baselineprofile) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://developer.huawei.com/repo/") }
        maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { setUrl("https://www.jitpack.io") } // jitpack should remain last in order according to its documentation
    }

    dependencies {
        classpath(deps.gradlePlugins.android)
        classpath(deps.gradlePlugins.hilt)
        classpath(deps.gradlePlugins.huaweiAGConnectPlugin)
        classpath(deps.gradlePlugins.laboratory)
        classpath(files("build-logic/utils/build/libs/config-1.0.jar"))
        classpath(files("build-logic/utils/build/libs/utils-1.0.jar"))
        classpath(files("build-logic/v.ersion-generator/build/libs/version-generator-1.0.jar"))
    }
}

copy {
    from(file("$rootDir/.scripts/git-hooks"))
    into(file("$rootDir/.git/hooks"))
    filePermissions.set(DefaultConfigurableFilePermissions(0b0111101101)) // -rwxr-xr-x
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.layout.buildDirectory)
}

dependencies {
    "detektPlugins"(deps.cccDetektPlugins)
    "detektPlugins"(deps.gradlePlugins.detekt)
    "detektPlugins"(deps.gradlePlugins.detektFormatting)
}
