package eu.ccc.mobile.convention.composeTest

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension

internal fun CommonExtension.applyComposeUnitTestsCommons() {
    if (this is LibraryExtension) {
        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
    if (this is ApplicationExtension) {
        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}