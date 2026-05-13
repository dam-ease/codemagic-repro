package eu.ccc.mobile.utils

object ContinuousIntegration {

    @JvmStatic
    fun isCi() = System.getenv("CI") == "true"

    fun getCurrentBuildNumber() = getBitriseBuildNumberOrNull() ?: 1

    private fun getBitriseBuildNumberOrNull() = System.getenv("PROJECT_BUILD_NUMBER")?.toIntOrNull()
}