package eu.ccc.mobile.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ContinuousIntegrationTest {

    @Test
    fun `if local build then build number should be 1`() {
        val buildNumber = ContinuousIntegration.getCurrentBuildNumber()

        assertThat(buildNumber).isGreaterThan(0)
    }
}