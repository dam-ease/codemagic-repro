package eu.ccc.mobile.utils

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val PROCESS_TIMEOUT_MINUTES = 2L

fun String.runCommand() {
    toProcess().waitFor(PROCESS_TIMEOUT_MINUTES, TimeUnit.MINUTES)
}

fun String.runCommandForResult(): String {
    var result: String? = null
    var reader: BufferedReader? = null

    try {
        val process = toProcess()
        process.waitFor(PROCESS_TIMEOUT_MINUTES, TimeUnit.MINUTES)

        reader = process.inputStream.bufferedReader()
        result = reader.readText()
    } catch (e: IOException) {
        @Suppress("PrintStackTrace")
        e.printStackTrace()
    } finally {
        reader?.close()
    }

    return result ?: error("Could not get results when evaluating command: $this")
}

private fun String.toProcess(): Process {
    val parts = this.split("\\s".toRegex())
    return ProcessBuilder(*parts.toTypedArray())
        .directory(File("."))
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
}