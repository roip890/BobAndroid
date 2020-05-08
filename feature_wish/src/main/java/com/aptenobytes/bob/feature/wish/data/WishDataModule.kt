package com.aptenobytes.bob.feature.wish.data

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import com.aptenobytes.bob.feature.wish.data.cache.cacheDataModule
import com.aptenobytes.bob.feature.wish.data.db.localDataModule
import com.aptenobytes.bob.feature.wish.data.network.networkDataModule
import com.aptenobytes.bob.feature.wish.data.prefs.sharedPreferencesDataModule
import org.kodein.di.Kodein

internal val dataModule = Kodein.Module("${FEATURE_NAME}DataModule") {
    import(networkDataModule)
    import(localDataModule)
    import(cacheDataModule)
    import(sharedPreferencesDataModule)
}
