package eu.ccc.mobile.convention.java

import eu.ccc.mobile.config.BuildConfig
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType

internal fun Project.configureJava() {
    tasks.withType(JavaCompile::class).configureEach {
        options.isFork = true
        sourceCompatibility = BuildConfig.JAVA_VERSION_NAME
        targetCompatibility = BuildConfig.JAVA_VERSION_NAME
    }
}