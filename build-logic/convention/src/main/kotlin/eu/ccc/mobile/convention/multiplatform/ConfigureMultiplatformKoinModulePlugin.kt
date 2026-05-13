package eu.ccc.mobile.convention.multiplatform

import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ConfigureMultiplatformKoinModulePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        configure<KotlinMultiplatformExtension> {
            with(sourceSets) {
                commonMain.dependencies {
                    implementation(project.dependencies.platform(deps.findLibrary("koin-bom").get()))
                    implementation(deps.findLibrary("koin-core").get())
                }
                commonTest.dependencies {
                    implementation(deps.findLibrary("koin-test").get())
                }
            }
        }
    }
}