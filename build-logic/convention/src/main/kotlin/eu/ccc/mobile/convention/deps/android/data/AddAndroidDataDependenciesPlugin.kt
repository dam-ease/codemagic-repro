package eu.ccc.mobile.convention.deps.android.data

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AddAndroidDataDependenciesPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        dependencies {
            "implementation"(project(":shared:platform"))
        }
    }
}
