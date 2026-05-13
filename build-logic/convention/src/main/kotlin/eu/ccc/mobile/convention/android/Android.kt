package eu.ccc.mobile.convention.android

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.android.build.api.dsl.LibraryExtension
import eu.ccc.mobile.config.BuildConfig
import eu.ccc.mobile.convention.utils.deps
import eu.ccc.mobile.utils.ContinuousIntegration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun KotlinMultiplatformExtension.configureBaseAndroid() {
    configure<KotlinMultiplatformAndroidLibraryTarget> {
        compileSdk = 36
        minSdk = 26
        withJava()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        withHostTest {}
        packaging.resources {
            merges.add("META-INF/COPYRIGHT")
            merges.add("META-INF/LICENSE.md")
            merges.add("META-INF/LICENSE-notice.md")
            excludes.add("META-INF/gradle/incremental.annotation.processors")
            excludes.add("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
        }
    }
}

@Suppress("MaxLineLength")
internal fun Project.configureAndroid(extension: KotlinMultiplatformExtension) = with(extension) {
    configureBaseAndroid()

    compilerOptions {
        freeCompilerArgs.addAll(
            "-P",
            "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=eu.ccc.mobile.shared.annotations.CommonParcelize"
        )
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(deps.findLibrary("arrow-bom").get()))
            implementation(deps.findLibrary("arrow-core").get())
            implementation(deps.findLibrary("arrow-coroutines").get())
            implementation(deps.findLibrary("kermit").get())
        }
        getByName("androidHostTest").dependencies {
            implementation(deps.findBundle("test-multiplatform").get())
        }
    }
}

internal fun Project.configureAndroid(extension: CommonExtension) {
    if (extension is ApplicationExtension) {
        configureApplication(extension)

        with(extension) {
            defaultConfig {
                compileSdk = 36
                minSdk = 26
                targetSdk = 35
                vectorDrawables.useSupportLibrary = true
            }

            compileOptions {
                sourceCompatibility = BuildConfig.JAVA_VERSION
                targetCompatibility = BuildConfig.JAVA_VERSION
            }

            testOptions {
                unitTests.all { test ->
                    test.useJUnitPlatform()
                }
            }

            buildFeatures.viewBinding = true

            packaging.resources {
                merges.add("META-INF/COPYRIGHT")
                merges.add("META-INF/LICENSE.md")
                merges.add("META-INF/LICENSE-notice.md")
                excludes.add("META-INF/gradle/incremental.annotation.processors")
                excludes.add("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
            }
        }
    }

    if (extension is LibraryExtension) {
        with(extension) {
            defaultConfig {
                compileSdk = 36
                minSdk = 26
                vectorDrawables.useSupportLibrary = true
            }

            compileOptions {
                sourceCompatibility = BuildConfig.JAVA_VERSION
                targetCompatibility = BuildConfig.JAVA_VERSION
            }

            testOptions {
                unitTests.all { test ->
                    test.useJUnitPlatform()
                }
            }

            buildFeatures.viewBinding = true

            packaging.resources {
                merges.add("META-INF/COPYRIGHT")
                merges.add("META-INF/LICENSE.md")
                merges.add("META-INF/LICENSE-notice.md")
                excludes.add("META-INF/gradle/incremental.annotation.processors")
                excludes.add("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
            }
        }
    }

    dependencies {
        "implementation"(platform(deps.findLibrary("arrow-bom").get()))
        "implementation"(deps.findLibrary("arrow-core").get())
        "implementation"(deps.findLibrary("arrow-coroutines").get())
        "implementation"(deps.findLibrary("kermit").get())

        "testImplementation"(deps.findBundle("test").get())
    }
}

internal fun Project.configureApplication(applicationExtension: ApplicationExtension) {
    with(applicationExtension) {
        lint {
            abortOnError = false
            checkDependencies = true
            checkReleaseBuilds = ContinuousIntegration.isCi()
            xmlOutput = project.rootProject.file("build/reports/lint/lint-results.xml")
            disable += setOf("ContentDescription", "PrivateResource", "Typos")
        }
    }
}