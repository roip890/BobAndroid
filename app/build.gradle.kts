import com.android.build.gradle.internal.dsl.BaseFlavor
import com.android.build.gradle.internal.dsl.DefaultConfig
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.BaseVariantOutput

var kotlin_version = CoreVersion.KOTLIN
var core_ktx_version = LibraryVersion.CORE_KTX

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.KTLINT_GRADLE)
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
        applicationId = AndroidConfig.ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        multiDexEnabled = true

        buildConfigFieldFromGradleProperty("apiBaseUrl")
        buildConfigFieldFromGradleProperty("apiToken")

        buildConfigField("FEATURE_MODULE_NAMES", getDynamicFeatureModuleNames())
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }

        testOptions {
            unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    // Each feature module that is included in settings.gradle.kts is added here as dynamic feature
    dynamicFeatures = ModuleDependency.getDynamicFeatureModules().toMutableSet()

    lintOptions {
        // By default lint does not check test sources, but setting this option means that lint will not even parse them
        isIgnoreTestSources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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
    // base library
    api(project(ModuleDependency.LIBRARY_BASE))

    // material
    api(LibraryDependency.MATERIAL)

    // layouts
    api(LibraryDependency.SUPPORT_CONSTRAINT_LAYOUT)
    api(LibraryDependency.COORDINATOR_LAYOUT)
    api(LibraryDependency.RECYCLER_VIEW)
    api(LibraryDependency.FRAGMENT_KTX)

    // navigation
    api(LibraryDependency.NAVIGATION_FRAGMENT_KTX)
    api(LibraryDependency.NAVIGATION_UI_KTX)
    api(LibraryDependency.NAVIGATION_RUNTIME)

    // ok http
    implementation(LibraryDependency.OK_HTTP)
    implementation(LibraryDependency.LOGGING_INTERCEPTOR)

    // play core
    implementation(LibraryDependency.PLAY_CORE)

    // stetho
    implementation(LibraryDependency.STETHO)
    implementation(LibraryDependency.STETHO_OK_HTTP)

    // retrofit
    api(LibraryDependency.RETROFIT)
    api(LibraryDependency.RETROFIT_MOSHI_CONVERTER)

    // lottie
    api(LibraryDependency.LOTTIE)

    // data binding
    kapt(LibraryDependency.DATA_BINDING_COMPILER_KTX)

    // room
    api(LibraryDependency.ROOM_RUNTIME)
    kapt(LibraryDependency.ROOM_COMPILER)
    api(LibraryDependency.ROOM_KTX)

    // moshi
    implementation(LibraryDependency.MOSHI)
    implementation(LibraryDependency.MOSHI_ADAPTERS)
    kapt(LibraryDependency.MOSHI_CODE_GEN)

    // iconics
    implementation(LibraryDependency.ICONICS)
    implementation(LibraryDependency.ICONICS_GOOGLE_MATERIAL)
    implementation(LibraryDependency.ICONICS_MATERIAL_DESIGN_ICONIC)
    implementation(LibraryDependency.ICONICS_FONTAWESOME)

    // tests
    addTestDependencies()
    implementation("androidx.core:core-ktx:${core_ktx_version}")
    implementation(kotlin("stdlib", kotlin_version))
}

fun BaseFlavor.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, propertyValue)
}

fun getDynamicFeatureModuleNames() = ModuleDependency.getDynamicFeatureModules()
    .map { it.replace(":feature_", "") }
    .toSet()

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }

fun DefaultConfig.buildConfigField(name: String, value: Set<String>) {
    // Generates String that holds Java String Array code
    val strValue = value.joinToString(prefix = "{", separator = ",", postfix = "}", transform = { "\"$it\"" })
    buildConfigField("String[]", name, strValue)
}
repositories {
    mavenCentral()
}

val bundletoolJar = project.rootDir.resolve("third_party/bundletool/bundletool-all-0.13.0.jar")

android.applicationVariants.all(object : Action<ApplicationVariant> {
    override fun execute(variant: ApplicationVariant) {
        variant.outputs.forEach { output: BaseVariantOutput? ->
            (output as? com.android.build.gradle.api.ApkVariantOutput)?.let { apkOutput: ApkVariantOutput ->
                var filePath = apkOutput.outputFile.absolutePath
                filePath = filePath.replaceAfterLast(".", "aab")
                filePath = filePath.replace("build/outputs/apk/", "build/outputs/bundle/")
                var outputPath = filePath.replace("build/outputs/bundle/", "build/outputs/apks/")
                outputPath = outputPath.replaceAfterLast(".", "apks")

                tasks.register<JavaExec>("buildApks${variant.name.capitalize()}") {
                    classpath = files(bundletoolJar)
                    args = listOf(
                        "build-apks",
                        "--overwrite",
                        "--local-testing",
                        "--bundle",
                        filePath,
                        "--output",
                        outputPath
                    )
                    dependsOn("bundle${variant.name.capitalize()}")
                }

                tasks.register<JavaExec>("installApkSplitsForTest${variant.name.capitalize()}") {
                    classpath = files(bundletoolJar)
                    args = listOf("install-apks", "--apks", outputPath)
                    dependsOn("buildApks${variant.name.capitalize()}")
                }

            }
        }
    }
})
