package com.aptenobytes.bob.feature.auth.data.db

import com.aptenobytes.bob.feature.auth.FEATURE_NAME
import com.aptenobytes.bob.feature.auth.data.db.datasource.AuthRoomDataSourceImpl
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthLocalDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal val localDataModule = Kodein.Module("${FEATURE_NAME}RoomDataModule") {

    bind<AuthDatabase>() with singleton {
        AuthDatabase(
            instance()
        )
    }

    bind<AuthDao>() with provider { instance<AuthDatabase>().authDao() }

    bind<AuthLocalDataSource>() with singleton {
        AuthRoomDataSourceImpl(
            instance()
        )
    }

}
