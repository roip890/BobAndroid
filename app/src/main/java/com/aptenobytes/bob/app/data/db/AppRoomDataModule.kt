package com.aptenobytes.bob.app.data.db

import com.aptenobytes.bob.app.data.db.datasource.AppRoomDataSourceImpl
import com.aptenobytes.bob.app.domain.datasource.AppLocalDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal val localDataModule = Kodein.Module("AppRoomDataModule") {

    bind<AppDatabase>() with singleton {
        AppDatabase(
            instance()
        )
    }

    bind<AppDao>() with provider { instance<AppDatabase>().appDao() }

    bind<AppLocalDataSource>() with singleton {
        AppRoomDataSourceImpl(
            instance()
        )
    }

}
