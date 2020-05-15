package com.aptenobytes.bob.feature.profile.data

import com.aptenobytes.bob.feature.profile.FEATURE_NAME
import com.aptenobytes.bob.feature.profile.data.cache.cacheDataModule
import com.aptenobytes.bob.feature.profile.data.db.localDataModule
import com.aptenobytes.bob.feature.profile.data.network.networkDataModule
import com.aptenobytes.bob.feature.profile.data.prefs.sharedPreferencesDataModule
import org.kodein.di.Kodein

internal val dataModule = Kodein.Module("${FEATURE_NAME}DataModule") {
    import(networkDataModule)
    import(localDataModule)
    import(cacheDataModule)
    import(sharedPreferencesDataModule)
}
