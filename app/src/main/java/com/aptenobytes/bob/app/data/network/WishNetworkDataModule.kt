package com.aptenobytes.bob.app.data.network

import com.aptenobytes.bob.app.data.network.datasource.AppNetworkDataSourceImpl
import com.aptenobytes.bob.app.data.network.retrofit.service.AppRetrofitService
import com.aptenobytes.bob.app.domain.datasource.AppNetworkDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val networkDataModule = Kodein.Module("AppDataModule") {

    bind<AppNetworkDataSource>() with singleton { AppNetworkDataSourceImpl(instance()) }

    bind() from singleton { instance<Retrofit>().create(AppRetrofitService::class.java) }
}
