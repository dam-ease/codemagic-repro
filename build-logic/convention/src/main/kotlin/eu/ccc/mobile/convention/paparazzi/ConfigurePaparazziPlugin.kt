package eu.ccc.mobile.convention.paparazzi

import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigurePaparazziPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply(deps.findPlugin("paparazzi").get().get().pluginId)
    }
}