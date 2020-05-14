plugins {
    id(GradlePluginId.ANDROID_DYNAMIC_FEATURE)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.SAFE_ARGS)
    id(GradlePluginId.KAPT)
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

    dataBinding.isEnabled = true

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

    addTestDependencies()
}
