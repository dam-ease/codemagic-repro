package eu.ccc.mobile.convention.fladle

import com.osacky.flank.gradle.FladleConfig
import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class FladleConfigurationPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        plugins.apply(deps.findPlugin("fladle").get().get().pluginId)

        val fladleConfig = extensions.findByType<FladleConfig>() ?: return

        fladleConfig.configure(this)
    }
}