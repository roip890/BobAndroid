package com.aptenobytes.bob.app.data.prefs

import android.content.SharedPreferences
import com.aptenobytes.bob.app.data.prefs.datasource.AppSharedPreferencesDataSourceImpl
import com.aptenobytes.bob.app.domain.datasource.AppSharedPreferencesDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val sharedPreferencesDataModule = Kodein.Module("AppSharedPreferencesDataModule") {

    bind<SharedPreferences>() with singleton { AppSharedPreferences(instance()) }

    bind<AppSharedPreferencesDataSource>() with singleton { AppSharedPreferencesDataSourceImpl(instance(), instance()) }

}
