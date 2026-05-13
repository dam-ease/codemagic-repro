package eu.ccc.mobile.utils

import org.gradle.api.internal.tasks.compile.JavaCompilerArgumentsBuilder.LOGGER

@Suppress("TooGenericExceptionCaught")
fun safeCall(function: () -> Unit): Boolean = try {
    function()
    true
} catch (t: Throwable) {
    LOGGER.debug("SafeCall exception", t)
    false
}