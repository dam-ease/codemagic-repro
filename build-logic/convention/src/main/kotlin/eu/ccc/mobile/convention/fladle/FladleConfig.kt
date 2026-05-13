package eu.ccc.mobile.convention.fladle

import com.osacky.flank.gradle.FladleConfig
import eu.ccc.mobile.utils.ContinuousIntegration
import org.gradle.api.Project

internal fun FladleConfig.configure(project: Project) {
    projectId.set("synerise-mobile-app")
    variant.set("fake")

    val buildDirPath = project.layout.buildDirectory.get().asFile.path

    debugApk.set("$buildDirPath/outputs/apk/fake/app-fake.apk")
    instrumentationApk.set("$buildDirPath/outputs/apk/androidTest/fake/app-fake-androidTest.apk")

    if (ContinuousIntegration.isCi()) {
        serviceAccountCredentials.set(project.rootProject.layout.projectDirectory.file("firebase.json"))
    }

    autoGoogleLogin.set(false)
    devices.set(
        listOf(
            mapOf(
                "model" to "MediumPhone.arm",
                "version" to "36",
                "locale" to "pl_PL",
                "orientation" to "portrait"
            )
        )
    )
    directoriesToPull.set(
        listOf(
            @Suppress("SdCardPath")
            "/sdcard/screenshots"
        )
    )
    filesToDownload.set(
        listOf(
            ".*/screenshots/.*"
        )
    )
    environmentVariables.set(
        mapOf(
            "clearPackageData" to "true",
            "listener" to "eu.ccc.mobile.listener.ToastingRunListener"
        )
    )
    flakyTestAttempts.set(1)
    maxTestShards.set(15)
    performanceMetrics.set(false)
    recordVideo.set(true)
    runTimeout.set("45m")
    shardTime.set(240)
    smartFlankDisableUpload.set(true)
    smartFlankGcsPath.set("gs://tmp_flank/tmp/JUnitReport.xml")
    testRunnerClass.set("androidx.test.runner.AndroidJUnitRunner")
    testTimeout.set("25m")
    useOrchestrator.set(true)
}