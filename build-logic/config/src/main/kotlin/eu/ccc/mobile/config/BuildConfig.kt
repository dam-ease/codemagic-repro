package eu.ccc.mobile.config

import eu.ccc.mobile.utils.ContinuousIntegration
import eu.ccc.mobile.utils.wrap
import org.gradle.api.JavaVersion

object BuildConfig {

    const val JAVA_VERSION_CODE = 21
    val JAVA_VERSION = JavaVersion.VERSION_21
    val JAVA_VERSION_NAME = JAVA_VERSION.toString()
    val NUMBER = "${ContinuousIntegration.getCurrentBuildNumber()}".wrap()
}