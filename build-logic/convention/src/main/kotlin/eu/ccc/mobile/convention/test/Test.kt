package eu.ccc.mobile.convention.test

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

internal fun Project.configureTest() {
    tasks.withType(Test::class).configureEach {
        maxParallelForks = Runtime.getRuntime().availableProcessors() / 2
        reports.html.required.set(false)
    }
}