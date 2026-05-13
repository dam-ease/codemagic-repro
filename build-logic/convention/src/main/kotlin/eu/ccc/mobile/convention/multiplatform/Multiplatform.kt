package eu.ccc.mobile.convention.multiplatform

import eu.ccc.mobile.convention.android.configureAndroid
import eu.ccc.mobile.convention.android.configureBaseAndroid
import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun configureBaseTargets(multiplatformExtension: KotlinMultiplatformExtension) {
    with(multiplatformExtension) {
        configureBaseAndroid()
        iosArm64()
        iosSimulatorArm64()
    }
}

@Suppress("MaxLineLength")
internal fun Project.configureTargets(multiplatformExtension: KotlinMultiplatformExtension) {
    with(multiplatformExtension) {
        configureAndroid(multiplatformExtension)
        iosArm64()
        iosSimulatorArm64()

        with(sourceSets) {
            commonMain.dependencies {
                api(deps.findLibrary("kotlin-coroutines-core").get())
                implementation(project.dependencies.platform(deps.findLibrary("arrow-bom").get()))
                implementation(deps.findLibrary("arrow-core").get())
                implementation(deps.findLibrary("arrow-coroutines").get())
                implementation(deps.findLibrary("kermit").get())
            }
            commonTest.dependencies {
                implementation(deps.findBundle("test-kotlin").get())
            }
        }
    }
}