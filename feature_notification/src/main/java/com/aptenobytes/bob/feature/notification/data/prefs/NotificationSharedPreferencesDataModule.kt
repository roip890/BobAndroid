package com.aptenobytes.bob.feature.notification.data.prefs

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.notification.FEATURE_NAME
import com.aptenobytes.bob.feature.notification.data.prefs.datasource.NotificationSharedPreferencesDataSourceImpl
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationSharedPreferencesDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val sharedPreferencesDataModule = Kodein.Module("${FEATURE_NAME}SharedPreferencesDataModule") {

    bind<SharedPreferences>("notification") with singleton { NotificationSharedPreferences(instance()) }

    bind<NotificationSharedPreferencesDataSource>() with singleton { NotificationSharedPreferencesDataSourceImpl(instance("notification"), instance()) }

}
