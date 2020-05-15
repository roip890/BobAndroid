package com.aptenobytes.bob.feature.auth.data

import com.aptenobytes.bob.feature.auth.FEATURE_NAME
import com.aptenobytes.bob.feature.auth.data.cache.cacheDataModule
import com.aptenobytes.bob.feature.auth.data.db.localDataModule
import com.aptenobytes.bob.feature.auth.data.network.networkDataModule
import com.aptenobytes.bob.feature.auth.data.prefs.sharedPreferencesDataModule
import org.kodein.di.Kodein

internal val dataModule = Kodein.Module("${FEATURE_NAME}DataModule") {
    import(networkDataModule)
    import(localDataModule)
    import(cacheDataModule)
    import(sharedPreferencesDataModule)
}
