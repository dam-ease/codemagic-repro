package eu.ccc.mobile.convention.detekt

import eu.ccc.mobile.convention.utils.deps
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild

internal fun Project.configureDetekt() {
    plugins.apply(deps.findPlugin("detekt").get().get().pluginId)

    val detektConfigFilePath = file("$rootDir/detekt-config.yml")

    tasks.withType(Detekt::class.java).configureEach {
        basePath = projectDir.path
        parallel = true
        setSource(files(projectDir))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
        config.setFrom(detektConfigFilePath)
        reports {
            xml.required.set(true)
            html.required.set(false)
            txt.required.set(false)
        }
    }

    tasks.register("staticAnalysis", GradleBuild::class.java) {
        dependsOn("detekt")
    }
}