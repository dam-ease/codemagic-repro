package eu.ccc.mobile.convention.android

import com.android.build.api.dsl.CommonExtension
import eu.ccc.mobile.convention.java.configureJava
import eu.ccc.mobile.convention.kotlin.configureKotlin
import eu.ccc.mobile.convention.test.configureTest
import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ConfigureAndroidModulePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        with(plugins) {
            apply(deps.findPlugin("kotlinSerialization").get().get().pluginId)
            apply(deps.findPlugin("parcelize").get().get().pluginId)
        }

        configureKotlin()

        configureJava()

        configureTest()

        configureAndroid(extensions.getByType<CommonExtension>())

        dependencies {
            "implementation"(deps.findLibrary("kotlin-serialization").get())
        }
    }
}