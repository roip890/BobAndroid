package com.aptenobytes.bob.app.data

import com.aptenobytes.bob.app.data.cache.cacheDataModule
import com.aptenobytes.bob.app.data.db.localDataModule
import com.aptenobytes.bob.app.data.network.networkDataModule
import com.aptenobytes.bob.app.data.prefs.sharedPreferencesDataModule
import org.kodein.di.Kodein

internal val dataModule = Kodein.Module("AppDataModule") {
    import(networkDataModule)
    import(localDataModule)
    import(cacheDataModule)
    import(sharedPreferencesDataModule)
}
