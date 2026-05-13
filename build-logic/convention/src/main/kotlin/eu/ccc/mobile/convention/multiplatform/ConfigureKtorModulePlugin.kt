package eu.ccc.mobile.convention.multiplatform

import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ConfigureKtorModulePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        plugins.apply(deps.findPlugin("kotlinSerialization").get().get().pluginId)

        configure<KotlinMultiplatformExtension> {
            configureKtor(this)
        }
    }
}
