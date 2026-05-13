package eu.ccc.mobile.convention.moshix

import dev.zacsweers.moshix.ir.gradle.MoshiPluginExtension
import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ConfigureMoshixPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply(deps.findPlugin("moshix").get().get().pluginId)

        configure<MoshiPluginExtension> {
            enableSealed.set(true)
        }

        dependencies {
            "api"(deps.findLibrary("moshi-core").get())
            "implementation"(deps.findLibrary("moshi-adapters").get())
            "implementation"(deps.findLibrary("moshi-kotlin").get())
            "implementation"(deps.findLibrary("moshix-adapters").get())
            "implementation"(deps.findLibrary("moshix-sealed").get())
        }
    }
}