package eu.ccc.mobile.convention.multiplatform

import eu.ccc.mobile.convention.kotlin.configureKotlin
import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ConfigureMultiplatformModulePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        with(pluginManager) {
            apply(deps.findPlugin("multiplatform-android").get().get().pluginId)
            apply(deps.findPlugin("multiplatform").get().get().pluginId)
        }
        configure<KotlinMultiplatformExtension> {
            configureTargets(this)
        }
        configureKotlin()
    }
}