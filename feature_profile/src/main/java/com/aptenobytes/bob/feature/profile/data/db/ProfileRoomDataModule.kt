package com.aptenobytes.bob.feature.profile.data.db

import com.aptenobytes.bob.feature.profile.FEATURE_NAME
import com.aptenobytes.bob.feature.profile.data.db.datasource.ProfileRoomDataSourceImpl
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileLocalDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal val localDataModule = Kodein.Module("${FEATURE_NAME}RoomDataModule") {

    bind<ProfileDatabase>() with singleton {
        ProfileDatabase(
            instance()
        )
    }

    bind<ProfileDao>() with provider { instance<ProfileDatabase>().profileDao() }

    bind<ProfileLocalDataSource>() with singleton {
        ProfileRoomDataSourceImpl(
            instance()
        )
    }

}
