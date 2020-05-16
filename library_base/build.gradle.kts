var kotlin_version = CoreVersion.KOTLIN
var core_ktx_version = LibraryVersion.CORE_KTX
plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.KAPT)
}
apply {
    plugin("kotlin-android")
    plugin("kotlin-android-extensions")
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    testOptions {
        unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
    }

    kapt {
        javacOptions {
            option("-Xmaxerrs", 1000)
            option("-Werror")
        }
    }

    buildFeatures.dataBinding = true
    buildFeatures.viewBinding = true
}

dependencies {

    api(LibraryDependency.KOTLIN)
    api(LibraryDependency.KOTLIN_REFLECT)

    api(LibraryDependency.KODEIN)
    api(LibraryDependency.KODEIN_ANDROID_X)

    api(LibraryDependency.TIMBER)
    api(LibraryDependency.APP_COMPAT)
    api(LibraryDependency.COROUTINES_ANDROID)
    api(LibraryDependency.CORE_KTX)
    api(LibraryDependency.FRAGMENT_KTX)
    api(LibraryDependency.LIFECYCLE_EXTENSIONS)
    kapt(LibraryDependency.LIFECYCLE_COMPILER_KTX)
    api(LibraryDependency.LIFECYCLE_RUNTIME_KTX)
    api(LibraryDependency.LIFECYCLE_VIEW_MODEL_KTX)
    api(LibraryDependency.LIFECYCLE_LIVE_DATA_KTX)
    kapt(LibraryDependency.DATA_BINDING_COMPILER_KTX)
    api(LibraryDependency.COIL)

    api(LibraryDependency.SUPPORT_SWIPE_REFRESH_LAYOUT)
    api(LibraryDependency.SUPPORT_CONSTRAINT_LAYOUT)
    api(LibraryDependency.COORDINATOR_LAYOUT)
    api(LibraryDependency.RECYCLER_VIEW)
    api(LibraryDependency.MATERIAL)

    // circle image view
    implementation(LibraryDependency.CIRCLE_IMAGE_VIEW)

    // navigation
    api(LibraryDependency.NAVIGATION_FRAGMENT_KTX)
    api(LibraryDependency.NAVIGATION_UI_KTX)
    api(LibraryDependency.NAVIGATION_RUNTIME)
    api(LibraryDependency.NAVIGATION_DYNAMIC_FEATURE_FRAGMENT_KTX)

    // material dialog
    implementation(LibraryDependency.MATERIAL_DIALOGS)
    implementation(LibraryDependency.MATERIAL_DIALOGS_DATE_TIME)
    implementation(LibraryDependency.MATERIAL_DIALOGS_BOTTOM_SHEET)

    // icons
    implementation(LibraryDependency.ICONICS)
    implementation(LibraryDependency.ICONICS_GOOGLE_MATERIAL)
    implementation(LibraryDependency.ICONICS_MATERIAL_DESIGN_ICONIC)
    implementation(LibraryDependency.ICONICS_FONTAWESOME)
    implementation("androidx.core:core-ktx:${core_ktx_version}")
    implementation(kotlin("stdlib", kotlin_version))

}
repositories {
    mavenCentral()
}
