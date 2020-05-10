@file:Suppress("detekt.StringLiteralDuplication")

private object LibraryVersion {
    const val KODEIN = "6.5.1"
    const val RETROFIT = "2.7.1"
    const val OK_HTTP = "4.3.1"
    const val STETHO = "1.5.0"
    const val TIMBER = "4.7.1"
    const val PLAY_CORE = "1.6.4"
    const val APP_COMPAT = "1.1.0"
    const val RECYCLER_VIEW = "1.1.0"
    const val COORDINATOR_LAYOUT = "1.1.0"

    // 1.1.x version is required in order to support the dark theme functionality in
    // Android Q (adds Theme.MaterialComponents.DayNight)
    const val MATERIAL = "1.1.0"
    const val CONSTRAINT_LAYOUT = "1.1.3"
    const val SWIPE_REFRESH_LAYOUT = "1.0.0"
    const val CORE_KTX = "1.1.0"
    const val FRAGMENT_KTX = "1.1.0"
    const val LIFECYCLE = "2.2.0-rc02"
    const val DATA_BINDING = "3.6.3"
    const val COIL = "0.9.1"
    const val K_ANDROID = "0.8.8@aar"
    const val LOTTIE = "3.3.1"
    const val MOSHI = "1.9.2"

    // third party libs
    const val MATERIAL_DIALOGS = "3.3.0"
}

private object IconsVersion {
    const val ICONICS = "5.0.2"
    const val ICONICS_GOOGLE_MATERIAL = "3.0.1.4.original-kotlin@aar"
    const val ICONICS_MATERIAL_DESIGN_ICONIC = "2.2.0.6-kotlin@aar"
    const val ICONICS_FONTAWESOME = "5.9.0.0-kotlin@aar"
    const val ICONICS_OCTICONS = "3.2.0.6-kotlin@aar"
    const val ICONICS_METEOCONS = "1.1.0.5-kotlin@aar"
    const val ICONICS_COMMUNITY_MATERIAL = "5.0.45.1-kotlin@aar"
    const val ICONICS_WEATHER_ICONS = "2.0.10.5-kotlin@aar"
    const val ICONICS_TYPEICONS = "2.0.7.5-kotlin@aar"
    const val ICONICS_ENTYPO = "1.0.0.5-kotlin@aar"
    const val ICONICS_DEVICON = "2.0.0.5-kotlin@aar"
    const val ICONICS_FOUNDATION_ICONS = "3.0.0.5-kotlin@aar"
    const val ICONICS_IONICONS = "2.0.1.5-kotlin@aar"
    const val ICONICS_PIXEDEN_7_STROKE = "1.2.0.3-kotlin@aar"
    const val ICONICS_MATERIAL_DESIGN_ICONS_DX = "5.0.1.0-kotlin@aar"
}

object LibraryDependency {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${CoreVersion.KOTLIN}"

    // Required by Android dynamic feature modules and SafeArgs
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN}"
    const val KODEIN = "org.kodein.di:kodein-di-generic-jvm:${LibraryVersion.KODEIN}"
    const val KODEIN_ANDROID_X = "org.kodein.di:kodein-di-framework-android-x:${LibraryVersion.KODEIN}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
    const val RETROFIT_MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${LibraryVersion.RETROFIT}"

    // Retrofit will use okhttp 4 (it tas binary capability with okhttp 3)
    // See: https://square.github.io/okhttp/upgrading_to_okhttp_4/
    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${LibraryVersion.OK_HTTP}"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.OK_HTTP}"
    const val STETHO = "com.facebook.stetho:stetho:${LibraryVersion.STETHO}"
    const val STETHO_OK_HTTP = "com.facebook.stetho:stetho-okhttp3:${LibraryVersion.STETHO}"
    const val TIMBER = "com.jakewharton.timber:timber:${LibraryVersion.TIMBER}"
    const val SUPPORT_CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${LibraryVersion.CONSTRAINT_LAYOUT}"
    const val SUPPORT_SWIPE_REFRESH_LAYOUT =
        "androidx.swiperefreshlayout:swiperefreshlayout:${LibraryVersion.SWIPE_REFRESH_LAYOUT}"
    const val PLAY_CORE = "com.google.android.play:core:${LibraryVersion.PLAY_CORE}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${LibraryVersion.APP_COMPAT}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${LibraryVersion.RECYCLER_VIEW}"
    const val COORDINATOR_LAYOUT = "androidx.coordinatorlayout:coordinatorlayout:${LibraryVersion.COORDINATOR_LAYOUT}"
    const val MATERIAL = "com.google.android.material:material:${LibraryVersion.MATERIAL}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersion.COROUTINES_ANDROID}"
    const val CORE_KTX = "androidx.core:core-ktx:${LibraryVersion.CORE_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${LibraryVersion.FRAGMENT_KTX}"

    const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${LibraryVersion.LIFECYCLE}"
    const val LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${LibraryVersion.LIFECYCLE}"
    const val LIFECYCLE_COMPILER_KTX =
        "androidx.lifecycle:lifecycle-compiler:${LibraryVersion.LIFECYCLE}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.LIFECYCLE}"
    const val LIFECYCLE_LIVE_DATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.LIFECYCLE}"

    const val DATA_BINDING_COMPILER_KTX =
        "androidx.databinding:databinding-compiler:${LibraryVersion.DATA_BINDING}"

    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${CoreVersion.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${CoreVersion.NAVIGATION}"
    const val NAVIGATION_RUNTIME = "androidx.navigation:navigation-runtime-ktx:${CoreVersion.NAVIGATION}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${CoreVersion.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${CoreVersion.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${CoreVersion.ROOM}"
    const val COIL = "io.coil-kt:coil:${LibraryVersion.COIL}"
    const val K_ANDROID = "com.pawegio.kandroid:kandroid:${LibraryVersion.K_ANDROID}"

    // moshi
    const val MOSHI = "com.squareup.moshi:moshi-kotlin:${LibraryVersion.MOSHI}"
    const val MOSHI_CODE_GEN = "com.squareup.moshi:moshi-kotlin-codegen:${LibraryVersion.MOSHI}"
    const val MOSHI_ADAPTERS = "com.squareup.moshi:moshi-adapters:${LibraryVersion.MOSHI}"

    // lottie
    const val LOTTIE = "com.airbnb.android:lottie:${LibraryVersion.LOTTIE}"

    // material dialog
    const val MATERIAL_DIALOGS = "com.afollestad.material-dialogs:core:${LibraryVersion.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_INPUTS = "com.afollestad.material-dialogs:input:${LibraryVersion.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_FILES = "com.afollestad.material-dialogs:files:${LibraryVersion.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_COLOR = "com.afollestad.material-dialogs:color:${LibraryVersion.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_DATE_TIME = "com.afollestad.material-dialogs:datetime:${LibraryVersion.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_BOTTOM_SHEET = "com.afollestad.material-dialogs:bottomsheets:${LibraryVersion.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_LIFECYCLE = "com.afollestad.material-dialogs:lifecycle:${LibraryVersion.MATERIAL_DIALOGS}"

    // icons
    const val ICONICS = "com.mikepenz:iconics-core:${IconsVersion.ICONICS}"
    const val ICONICS_VIEWS = "com.mikepenz:iconics-views:${IconsVersion.ICONICS}"

    const val ICONICS_GOOGLE_MATERIAL = "com.mikepenz:google-material-typeface:${IconsVersion.ICONICS_GOOGLE_MATERIAL}"
    const val ICONICS_MATERIAL_DESIGN_ICONIC = "com.mikepenz:material-design-iconic-typeface:${IconsVersion.ICONICS_MATERIAL_DESIGN_ICONIC}"
    const val ICONICS_FONTAWESOME = "com.mikepenz:fontawesome-typeface:${IconsVersion.ICONICS_FONTAWESOME}"
    const val ICONICS_OCTICONS = "com.mikepenz:octicons-typeface:${IconsVersion.ICONICS_OCTICONS}"
    const val ICONICS_METEOCONS = "com.mikepenz:meteocons-typeface:${IconsVersion.ICONICS_METEOCONS}"
    const val ICONICS_COMMUNITY_MATERIAL = "com.mikepenz:community-material-typeface:${IconsVersion.ICONICS_COMMUNITY_MATERIAL}"
    const val ICONICS_WEATHER_ICONS = "com.mikepenz:weather-icons-typeface:${IconsVersion.ICONICS_WEATHER_ICONS}"
    const val ICONICS_TYPEICONS = "com.mikepenz:typeicons-typeface:${IconsVersion.ICONICS_TYPEICONS}"
    const val ICONICS_ENTYPO = "com.mikepenz:entypo-typeface:${IconsVersion.ICONICS_ENTYPO}"
    const val ICONICS_DEVICON = "com.mikepenz:devicon-typeface:${IconsVersion.ICONICS_DEVICON}"
    const val ICONICS_FOUNDATION_ICONS = "com.mikepenz:foundation-icons-typeface:${IconsVersion.ICONICS_FOUNDATION_ICONS}"
    const val ICONICS_IONICONS = "com.mikepenz:ionicons-typeface:${IconsVersion.ICONICS_IONICONS}"
    const val ICONICS_PIXEDEN_7_STROKE = "com.mikepenz:pixeden-7-stroke-typeface:${IconsVersion.ICONICS_PIXEDEN_7_STROKE}"
    const val ICONICS_MATERIAL_DESIGN_ICONS_DX = "com.mikepenz:material-design-icons-dx-typeface:${IconsVersion.ICONICS_MATERIAL_DESIGN_ICONS_DX}"

}
