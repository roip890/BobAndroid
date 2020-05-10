package com.aptenobytes.bob

import coil.ImageLoader
import com.aptenobytes.bob.app.data.dataModule
import com.aptenobytes.bob.app.data.network.interceptors.UserAgentInterceptor
import com.aptenobytes.bob.app.data.utils.moshi.MoshiArrayListJsonAdapter
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArrayAdapter
import com.aptenobytes.bob.app.domain.domainModule
import com.aptenobytes.bob.app.presentation.presentationModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val appModule = Kodein.Module("appModule") {

    import(presentationModule)
    import(domainModule)
    import(dataModule)

    bind() from singleton { UserAgentInterceptor() }

    bind() from singleton { SingleToArrayAdapter.SingleToArrayAdapterFactory() }

    bind<HttpLoggingInterceptor>() with singleton {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    bind<Moshi.Builder>() with singleton { Moshi.Builder()
        .add(MoshiArrayListJsonAdapter.FACTORY)
        .add(Date::class.java, Rfc3339DateJsonAdapter())
    }

    bind<Retrofit.Builder>() with singleton { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with singleton { OkHttpClient.Builder() }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(instance<UserAgentInterceptor>())
            .addInterceptor(instance<HttpLoggingInterceptor>())
            .build()
    }

    bind<Moshi>() with singleton {
        instance<Moshi.Builder>()
            .add(instance<SingleToArrayAdapter.SingleToArrayAdapterFactory>())
            .build()
    }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(BuildConfig.GRADLE_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(instance<Moshi>()))
            .client(instance())
            .build()
    }

    bind() from singleton { ImageLoader(instance()) }
}
