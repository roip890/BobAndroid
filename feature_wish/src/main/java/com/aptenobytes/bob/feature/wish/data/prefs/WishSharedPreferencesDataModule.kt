package com.aptenobytes.bob.feature.wish.data.prefs

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import com.aptenobytes.bob.feature.wish.data.prefs.datasource.WishSharedPreferencesDataSourceImpl
import com.aptenobytes.bob.feature.wish.domain.datasource.WishSharedPreferencesDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val sharedPreferencesDataModule = Kodein.Module("${FEATURE_NAME}SharedPreferencesDataModule") {

    bind<SharedPreferences>(FEATURE_NAME) with singleton { WishSharedPreferences(instance()) }

    bind<WishSharedPreferencesDataSource>() with singleton { WishSharedPreferencesDataSourceImpl(instance(FEATURE_NAME), instance()) }

}
