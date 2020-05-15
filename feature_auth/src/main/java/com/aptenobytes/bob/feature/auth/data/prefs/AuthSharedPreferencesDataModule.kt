package com.aptenobytes.bob.feature.auth.data.prefs

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.auth.FEATURE_NAME
import com.aptenobytes.bob.feature.auth.data.prefs.datasource.AuthSharedPreferencesDataSourceImpl
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthSharedPreferencesDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val sharedPreferencesDataModule = Kodein.Module("${FEATURE_NAME}SharedPreferencesDataModule") {

    bind<SharedPreferences>(FEATURE_NAME) with singleton {
        AuthSharedPreferences(
            instance()
        )
    }

    bind<AuthSharedPreferencesDataSource>() with singleton { AuthSharedPreferencesDataSourceImpl(instance(FEATURE_NAME), instance()) }

}
