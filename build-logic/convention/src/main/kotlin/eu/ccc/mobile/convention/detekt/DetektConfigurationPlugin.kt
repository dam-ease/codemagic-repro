package eu.ccc.mobile.convention.detekt

import org.gradle.api.Plugin
import org.gradle.api.Project

class DetektConfigurationPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.configureDetekt()
    }
}