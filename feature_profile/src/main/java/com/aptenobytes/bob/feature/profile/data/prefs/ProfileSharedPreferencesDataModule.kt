package com.aptenobytes.bob.feature.profile.data.prefs

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.profile.FEATURE_NAME
import com.aptenobytes.bob.feature.profile.data.prefs.datasource.ProfileSharedPreferencesDataSourceImpl
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileSharedPreferencesDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val sharedPreferencesDataModule = Kodein.Module("${FEATURE_NAME}SharedPreferencesDataModule") {

    bind<SharedPreferences>(FEATURE_NAME) with singleton {
        ProfileSharedPreferences(
            instance()
        )
    }

    bind<ProfileSharedPreferencesDataSource>() with singleton { ProfileSharedPreferencesDataSourceImpl(instance(FEATURE_NAME), instance()) }

}
