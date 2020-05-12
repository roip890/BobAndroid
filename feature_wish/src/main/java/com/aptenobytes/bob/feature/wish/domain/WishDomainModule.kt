package com.aptenobytes.bob.feature.wish.domain

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepositoryImpl
import com.aptenobytes.bob.feature.wish.domain.usecase.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val domainModule = Kodein.Module("${FEATURE_NAME}DomainModule") {

    bind<WishRepository>() with singleton { WishRepositoryImpl(instance(), instance(), instance(), instance()) }

    bind() from singleton { GetWishesListFromSettingsUseCase(instance()) }

    bind() from singleton { GetWishesListUseCase(instance()) }

    bind() from singleton { GetWishDetailUseCase(instance()) }

    bind() from singleton { GetWishSettingsUseCase(instance()) }

    bind() from singleton { SetWishSettingsUseCase(instance()) }

    bind() from singleton { SetWishStatusUseCase(instance()) }

}
