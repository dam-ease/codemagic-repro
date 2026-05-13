package eu.ccc.mobile.convention.android

import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ConfigureAndroidKoinModulePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        dependencies {
            "implementation"(platform(deps.findLibrary("koin-bom").get()))
            "implementation"(deps.findLibrary("koin-android").get())
            "implementation"(deps.findLibrary("koin-androidx-compose").get())
            "implementation"(deps.findLibrary("koin-compose").get())
            "implementation"(deps.findLibrary("koin-compose-viewmodel").get())
            "testImplementation"(deps.findLibrary("koin-test").get())
        }
    }
}