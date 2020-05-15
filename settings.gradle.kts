
pluginManagement {

    // workaround because pluginManagement change after migrating from 5.6.4 to 6.4
    val coreVersion = object {
        val KOTLIN = "1.3.72"
        val COROUTINES_ANDROID = "1.3.5"
        val KTLINT = "0.36.0"
        val NAVIGATION = "2.3.0-alpha06"
        val ROOM = "2.2.5"
    }

    val gradlePluginVersion = object {
        val ANDROID_GRADLE = "4.0.0-rc01"
        val KTLINT_GRADLE = "9.2.1"
        val DETEKT = "1.4.0"
        val GRADLE_VERSION_PLUGIN = "0.22.0"
        val KOTLIN = coreVersion.KOTLIN
        val SAFE_ARGS = coreVersion.NAVIGATION
    }

    val gradlePluginId = object  {
        val DETEKT = "io.gitlab.arturbosch.detekt"
        val KTLINT_GRADLE = "org.jlleitschuh.gradle.ktlint"
        val ANDROID_APPLICATION = "com.android.application"
        val ANDROID_DYNAMIC_FEATURE = "com.android.dynamic-feature"
        val ANDROID_LIBRARY = "com.android.library"
        val KOTLIN_JVM = "org.jetbrains.kotlin.jvm"
        val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
        val KOTLIN_ANDROID_EXTENSIONS = "org.jetbrains.kotlin.android.extensions"
        val GRADLE_VERSION_PLUGIN = "com.github.ben-manes.versions"
        val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
        val KAPT = "org.jetbrains.kotlin.kapt"
    }

    val gradleOldWayPlugins = object {
        val ANDROID_GRADLE = "com.android.tools.build:gradle:${gradlePluginVersion.ANDROID_GRADLE}"
        val SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${gradlePluginVersion.SAFE_ARGS}"
        val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${gradlePluginVersion.KOTLIN}"
    }
    // finish workaround because pluginManagement change after migrating from 5.6.4 to 6.4

    repositories {
        gradlePluginPortal()
        jcenter()
        google()
    }



    plugins {
        id(gradlePluginId.KAPT) version gradlePluginVersion.KOTLIN
        id(gradlePluginId.DETEKT) version gradlePluginVersion.DETEKT
        id(gradlePluginId.KTLINT_GRADLE) version gradlePluginVersion.KTLINT_GRADLE
        id(gradlePluginId.GRADLE_VERSION_PLUGIN) version gradlePluginVersion.GRADLE_VERSION_PLUGIN
        id(gradlePluginId.KOTLIN_JVM) version gradlePluginVersion.KOTLIN
        id(gradlePluginId.KOTLIN_ANDROID) version gradlePluginVersion.KOTLIN
        id(gradlePluginId.KOTLIN_ANDROID_EXTENSIONS) version gradlePluginVersion.KOTLIN
        id(gradlePluginId.ANDROID_APPLICATION) version gradlePluginVersion.ANDROID_GRADLE
        id(gradlePluginId.ANDROID_LIBRARY) version gradlePluginVersion.ANDROID_GRADLE
        id(gradlePluginId.ANDROID_DYNAMIC_FEATURE) version gradlePluginVersion.ANDROID_GRADLE
        id(gradlePluginId.SAFE_ARGS) version gradlePluginVersion.SAFE_ARGS
    }

    resolutionStrategy {

        eachPlugin {
            when (requested.id.id) {
                gradlePluginId.ANDROID_APPLICATION,
                gradlePluginId.ANDROID_LIBRARY,
                gradlePluginId.ANDROID_DYNAMIC_FEATURE -> useModule(gradleOldWayPlugins.ANDROID_GRADLE)
                gradlePluginId.SAFE_ARGS -> useModule(gradleOldWayPlugins.SAFE_ARGS)
            }
        }
    }
}


rootProject.buildFileName = "build.gradle.kts"

// before the workaround
//include(*ModuleDependency.getAllModules().toTypedArray())

// workaround because pluginManagement change after migrating from 5.6.4 to 6.4
include(":app")
include(":feature_wish")
include(":feature_profile")
include(":feature_notification")
include(":library_base")
include(":library_test_utils")
