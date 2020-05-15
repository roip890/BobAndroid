package com.aptenobytes.bob.feature.profile.data.network

import com.aptenobytes.bob.feature.profile.FEATURE_NAME
import com.aptenobytes.bob.feature.profile.data.network.datasource.ProfileNetworkDataSourceImpl
import com.aptenobytes.bob.feature.profile.data.network.retrofit.service.ProfileRetrofitService
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileNetworkDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val networkDataModule = Kodein.Module("${FEATURE_NAME}NetworkDataModule") {

    bind<ProfileNetworkDataSource>() with singleton { ProfileNetworkDataSourceImpl(instance()) }

    bind() from singleton { instance<Retrofit>().create(ProfileRetrofitService::class.java) }

}
