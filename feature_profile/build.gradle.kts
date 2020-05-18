var kotlin_version = CoreVersion.KOTLIN
var core_ktx_version = LibraryVersion.CORE_KTX
plugins {
    id(GradlePluginId.ANDROID_DYNAMIC_FEATURE)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.SAFE_ARGS)
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

    // This "test" source set is a fix for SafeArgs classes not being available when running Unit tests from cmd
    // See: https://issuetracker.google.com/issues/139242292
    sourceSets {
        getByName("test").java.srcDir("${project.rootDir}/app/build/generated/source/navigation-args/debug")
    }

    // Removes the need to mock need to mock classes that may be irrelevant from test perspective
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

    configurations.all {
        resolutionStrategy {
            force(GradlePluginId.ANTLR_RUNTIME)
            force(GradlePluginId.ANTLR_TOOL)
        }
    }

}

dependencies {
    implementation(project(ModuleDependency.APP))
    kapt(LibraryDependency.DATA_BINDING_COMPILER_KTX)
    api(LibraryDependency.ROOM_RUNTIME)
    kapt(LibraryDependency.ROOM_COMPILER)
    api(LibraryDependency.ROOM_KTX)

    // material dialog
    implementation(LibraryDependency.MATERIAL_DIALOGS)
    implementation(LibraryDependency.MATERIAL_DIALOGS_DATE_TIME)
    implementation(LibraryDependency.MATERIAL_DIALOGS_BOTTOM_SHEET)

    // iconics
    implementation(LibraryDependency.ICONICS)
    implementation(LibraryDependency.ICONICS_GOOGLE_MATERIAL)
    implementation(LibraryDependency.ICONICS_MATERIAL_DESIGN_ICONIC)
    implementation(LibraryDependency.ICONICS_FONTAWESOME)

    // moshi
    implementation(LibraryDependency.MOSHI)
    implementation(LibraryDependency.MOSHI_ADAPTERS)
    kapt(LibraryDependency.MOSHI_CODE_GEN)

    // circle image view
    implementation(LibraryDependency.CIRCLE_IMAGE_VIEW)

    // permissions
    implementation(LibraryDependency.K_PERMISSIONS)
    implementation(LibraryDependency.K_PERMISSIONS_COROUTINES)

    // image picker
    implementation(LibraryDependency.IMAGE_PICKER)

    // photo view
    implementation(LibraryDependency.PHOTO_VIEW)

    // zoom layout
    implementation(LibraryDependency.ZOOM_LAYOUT)

    // inline activity result
    implementation(LibraryDependency.INLINE_ACTIVITY_RESULT)


    addTestDependencies()
    implementation("androidx.core:core-ktx:${core_ktx_version}")
    implementation(kotlin("stdlib", kotlin_version))
}

repositories {
    mavenCentral()
}
