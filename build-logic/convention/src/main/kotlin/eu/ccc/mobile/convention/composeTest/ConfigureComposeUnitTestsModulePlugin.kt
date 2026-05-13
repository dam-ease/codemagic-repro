package eu.ccc.mobile.convention.composeTest

import com.android.build.api.dsl.CommonExtension
import eu.ccc.mobile.convention.utils.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ConfigureComposeUnitTestsModulePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        extensions.getByType<CommonExtension>().applyComposeUnitTestsCommons()

        dependencies {
            "testImplementation"(deps.findBundle("test.compose").get())
        }
    }
}