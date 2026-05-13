package eu.ccc.mobile.convention.deps.android.presentation

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AddAndroidPresentationDependenciesPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        dependencies {
            "implementation"(project(":translations"))
            "implementation"(project(":resources"))
        }
    }
}
