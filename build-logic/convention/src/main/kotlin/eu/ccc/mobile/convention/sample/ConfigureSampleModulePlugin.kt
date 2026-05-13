package eu.ccc.mobile.convention.sample

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class ConfigureSampleModulePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        plugins.apply("com.android.application")
        plugins.apply("eu.ccc.mobile.android")

        extensions.getByType<ApplicationExtension>().applySampleCommons(this)
    }
}