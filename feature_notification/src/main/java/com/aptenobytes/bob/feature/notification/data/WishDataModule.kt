package com.aptenobytes.bob.feature.notification.data

import com.aptenobytes.bob.feature.notification.FEATURE_NAME
import com.aptenobytes.bob.feature.notification.data.cache.cacheDataModule
import com.aptenobytes.bob.feature.notification.data.db.localDataModule
import com.aptenobytes.bob.feature.notification.data.network.networkDataModule
import com.aptenobytes.bob.feature.notification.data.prefs.sharedPreferencesDataModule
import org.kodein.di.Kodein

internal val dataModule = Kodein.Module("${FEATURE_NAME}DataModule") {
    import(networkDataModule)
    import(localDataModule)
    import(cacheDataModule)
    import(sharedPreferencesDataModule)
}
