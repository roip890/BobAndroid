package com.aptenobytes.bob.feature.wish.data.network

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import com.aptenobytes.bob.feature.wish.data.network.datasource.WishNetworkDataSourceImpl
import com.aptenobytes.bob.feature.wish.data.network.retrofit.service.WishRetrofitService
import com.aptenobytes.bob.feature.wish.domain.datasource.WishNetworkDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val networkDataModule = Kodein.Module("${FEATURE_NAME}NetworkDataModule") {

    bind<WishNetworkDataSource>() with singleton { WishNetworkDataSourceImpl(instance()) }

    bind() from singleton { instance<Retrofit>().create(WishRetrofitService::class.java) }
}
