package com.aptenobytes.bob.feature.auth.data.network

import com.aptenobytes.bob.feature.auth.FEATURE_NAME
import com.aptenobytes.bob.feature.auth.data.network.datasource.AuthNetworkDataSourceImpl
import com.aptenobytes.bob.feature.auth.data.network.retrofit.service.AuthRetrofitService
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthNetworkDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val networkDataModule = Kodein.Module("${FEATURE_NAME}NetworkDataModule") {

    bind<AuthNetworkDataSource>() with singleton { AuthNetworkDataSourceImpl(instance()) }

    bind() from singleton { instance<Retrofit>().create(AuthRetrofitService::class.java) }

}
