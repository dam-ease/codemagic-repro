package eu.ccc.mobile.convention.compose

import com.android.build.api.dsl.CommonExtension
import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

class ConfigureComposeModulePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        extensions.getByType<CommonExtension>().buildFeatures.compose = true

        with(plugins) {
            apply(deps.findPlugin("composeCompiler").get().get().pluginId)
            apply(deps.findPlugin("kotlinSerialization").get().get().pluginId)
        }

        dependencies {
            "implementation"(platform(deps.findLibrary("androidX-compose-bom").get()))
            "implementation"(deps.findLibrary("androidX-compose-ui").get())
            "implementation"(deps.findLibrary("androidX-compose-material3").get())
            "implementation"(deps.findLibrary("androidX-compose-material-icons").get())
            "implementation"(deps.findLibrary("androidX-compose-material-navigation").get())
            "implementation"(deps.findLibrary("androidX-compose-foundation").get())
            "implementation"(deps.findLibrary("androidX-compose-hiltNavigation").get())
            "debugImplementation"(deps.findLibrary("androidX-compose-uiTooling").get())
            "implementation"(deps.findLibrary("androidX-compose-preview").get())
            "implementation"(deps.findLibrary("androidX-compose-navigation").get())
            "implementation"(deps.findLibrary("androidX-lifecycle-compose").get())
            "implementation"(deps.findLibrary("androidX-fragment-compose").get())
            "implementation"(deps.findLibrary("androidX-compose-activity").get())
            "implementation"(deps.findLibrary("androidX-activity").get())
            "implementation"(deps.findLibrary("androidX-compose-navigation").get())
            "implementation"(deps.findLibrary("kotlin-serialization").get())
            "implementation"(project(":translations"))
            "implementation"(project(":resources"))
        }
    }
}
