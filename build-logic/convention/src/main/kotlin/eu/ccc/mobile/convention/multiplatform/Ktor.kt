package eu.ccc.mobile.convention.multiplatform

import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKtor(multiplatformExtension: KotlinMultiplatformExtension) {
    with(multiplatformExtension) {
        with(sourceSets) {
            commonMain.dependencies {
                implementation(deps.findBundle("ktor").get())
                implementation(deps.findLibrary("kotlin-serialization").get())
            }
            commonTest.dependencies {
                implementation(deps.findLibrary("ktor-client-mock").get())
            }
            androidMain.dependencies {
                implementation(deps.findLibrary("ktor-okhttp").get())
            }
        }
    }
}