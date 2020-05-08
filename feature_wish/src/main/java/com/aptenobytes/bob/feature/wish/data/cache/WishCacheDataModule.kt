package com.aptenobytes.bob.feature.wish.data.cache

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import com.aptenobytes.bob.feature.wish.data.cache.datasource.WishCacheDataSourceImpl
import com.aptenobytes.bob.feature.wish.domain.datasource.WishCacheDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

internal val cacheDataModule = Kodein.Module("${FEATURE_NAME}CacheDataModule") {

    bind<WishCacheDataSource>() with singleton {WishCacheDataSourceImpl()}

}
