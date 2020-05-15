package com.aptenobytes.bob.feature.profile.data.cache

import com.aptenobytes.bob.feature.profile.FEATURE_NAME
import com.aptenobytes.bob.feature.profile.data.cache.datasource.ProfileCacheDataSourceImpl
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileCacheDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

internal val cacheDataModule = Kodein.Module("${FEATURE_NAME}CacheDataModule") {

    bind<ProfileCacheDataSource>() with singleton { ProfileCacheDataSourceImpl() }

}
