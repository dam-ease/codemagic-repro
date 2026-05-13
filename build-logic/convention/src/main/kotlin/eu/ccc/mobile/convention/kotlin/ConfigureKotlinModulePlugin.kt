package eu.ccc.mobile.convention.kotlin

import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigureKotlinModulePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        with(pluginManager) {
            apply(deps.findPlugin("kotlin").get().get().pluginId)
        }

        project.configureKotlin()
    }
}