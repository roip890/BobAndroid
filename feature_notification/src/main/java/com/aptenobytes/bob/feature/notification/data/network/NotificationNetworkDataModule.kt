package com.aptenobytes.bob.feature.notification.data.network

import com.aptenobytes.bob.feature.notification.FEATURE_NAME
import com.aptenobytes.bob.feature.notification.data.network.datasource.NotificationNetworkDataSourceImpl
import com.aptenobytes.bob.feature.notification.data.network.retrofit.service.NotificationRetrofitService
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationNetworkDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val networkDataModule = Kodein.Module("${FEATURE_NAME}NetworkDataModule") {

    bind<NotificationNetworkDataSource>() with singleton { NotificationNetworkDataSourceImpl(instance()) }

    bind() from singleton { instance<Retrofit>().create(NotificationRetrofitService::class.java) }
}
