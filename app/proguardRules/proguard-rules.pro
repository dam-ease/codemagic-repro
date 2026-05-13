# https://developer.android.com/build/releases/agp-9-1-0-release-notes?hl=pl#r8-changes causes crashes in PayU sdk
-dontrepackage

-keep public class com.android.installreferrer.** { *; }
-keep class eu.ccc.mobile.view.productlist.vertical.ProductListType { *; }

# Retrofit
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

-keep class eu.ccc.mobile.model.** { *; }
-keep class * extends eu.ccc.mobile.navigation.domain.data.NavigationRequest
-keep class * extends eu.ccc.mobile.navigation.domain.model.NavigationResult

# Moshi
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }

-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

-keep class com.synerise.sdk.** { *; }

# Analytics
# Activity/Fragment subclass packages are also used in LifecycleLogger.
-keep class eu.ccc.mobile.analytics.** { *; }
-keep public class * extends android.app.Activity
-keep public class * extends androidx.fragment.app.Fragment