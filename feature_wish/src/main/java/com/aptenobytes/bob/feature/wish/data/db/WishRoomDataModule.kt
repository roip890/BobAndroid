package com.aptenobytes.bob.feature.wish.data.db

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import com.aptenobytes.bob.feature.wish.data.db.datasource.WishRoomDataSourceImpl
import com.aptenobytes.bob.feature.wish.domain.datasource.WishLocalDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal val localDataModule = Kodein.Module("${FEATURE_NAME}RoomDataModule") {

    bind<WishDatabase>() with singleton {
        WishDatabase(
            instance()
        )
    }

    bind<WishDao>() with provider { instance<WishDatabase>().wishDao() }

    bind<WishLocalDataSource>() with singleton {
        WishRoomDataSourceImpl(
            instance()
        )
    }

}
