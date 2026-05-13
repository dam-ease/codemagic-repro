plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("eu.ccc.mobile.android") {
            id = "eu.ccc.mobile.android"
            implementationClass = "eu.ccc.mobile.convention.android.ConfigureAndroidModulePlugin"
        }
        register("eu.ccc.mobile.android.koin") {
            id = "eu.ccc.mobile.android.koin"
            implementationClass = "eu.ccc.mobile.convention.android.ConfigureAndroidKoinModulePlugin"
        }
        register("eu.ccc.mobile.kotlin") {
            id = "eu.ccc.mobile.kotlin"
            implementationClass = "eu.ccc.mobile.convention.kotlin.ConfigureKotlinModulePlugin"
        }
        register("eu.ccc.mobile.sample") {
            id = "eu.ccc.mobile.sample"
            implementationClass = "eu.ccc.mobile.convention.sample.ConfigureSampleModulePlugin"
        }
        register("eu.ccc.mobile.compose") {
            id = "eu.ccc.mobile.compose"
            implementationClass = "eu.ccc.mobile.convention.compose.ConfigureComposeModulePlugin"
        }
        register("eu.ccc.mobile.compose.unittest") {
            id = "eu.ccc.mobile.compose.unittest"
            implementationClass = "eu.ccc.mobile.convention.composeTest.ConfigureComposeUnitTestsModulePlugin"
        }
        register("eu.ccc.mobile.deps.android.data") {
            id = "eu.ccc.mobile.deps.android.data"
            implementationClass = "eu.ccc.mobile.convention.deps.android.data.AddAndroidDataDependenciesPlugin"
        }
        register("eu.ccc.mobile.deps.android.presentation") {
            id = "eu.ccc.mobile.deps.android.presentation"
            implementationClass =
                "eu.ccc.mobile.convention.deps.android.presentation.AddAndroidPresentationDependenciesPlugin"
        }
        register("eu.ccc.mobile.deps.kotlin") {
            id = "eu.ccc.mobile.deps.kotlin"
            implementationClass = "eu.ccc.mobile.convention.deps.kotlin.AddKotlinDependenciesPlugin"
        }
        register("eu.ccc.mobile.detekt") {
            id = "eu.ccc.mobile.detekt"
            implementationClass = "eu.ccc.mobile.convention.detekt.DetektConfigurationPlugin"
        }
        register("eu.ccc.mobile.fladle") {
            id = "eu.ccc.mobile.fladle"
            implementationClass = "eu.ccc.mobile.convention.fladle.FladleConfigurationPlugin"
        }
        register("eu.ccc.mobile.multiplatform") {
            id = "eu.ccc.mobile.multiplatform"
            implementationClass = "eu.ccc.mobile.convention.multiplatform.ConfigureMultiplatformModulePlugin"
        }
        register("eu.ccc.mobile.multiplatform.koin") {
            id = "eu.ccc.mobile.multiplatform.koin"
            implementationClass = "eu.ccc.mobile.convention.multiplatform.ConfigureMultiplatformKoinModulePlugin"
        }
        register("eu.ccc.mobile.multiplatform.basic") {
            id = "eu.ccc.mobile.multiplatform.basic"
            implementationClass = "eu.ccc.mobile.convention.multiplatform.ConfigureMultiplatformBasicPlugin"
        }
        register("eu.ccc.mobile.multiplatform.ktor") {
            id = "eu.ccc.mobile.multiplatform.ktor"
            implementationClass = "eu.ccc.mobile.convention.multiplatform.ConfigureKtorModulePlugin"
        }
        register("eu.ccc.mobile.paparazzi") {
            id = "eu.ccc.mobile.paparazzi"
            implementationClass = "eu.ccc.mobile.convention.paparazzi.ConfigurePaparazziPlugin"
        }
        register("eu.ccc.mobile.moshix") {
            id = "eu.ccc.mobile.moshix"
            implementationClass = "eu.ccc.mobile.convention.moshix.ConfigureMoshixPlugin"
        }
    }
}

dependencies {
    implementation(deps.gradlePlugins.detekt)
    implementation(deps.gradlePlugins.fladle)
    implementation(deps.gradlePlugins.moshix)
    compileOnly(deps.gradlePlugins.android)
    compileOnly(deps.gradlePlugins.kotlin)

    implementation(project(":config"))
    implementation(project(":utils"))
}