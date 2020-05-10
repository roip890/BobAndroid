package com.aptenobytes.bob.feature.wish.domain

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepositoryImpl
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishesListUseCase
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishesSettingsUseCase
import com.aptenobytes.bob.feature.wish.domain.usecase.SetWishesSettingsUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val domainModule = Kodein.Module("${FEATURE_NAME}DomainModule") {

    bind<WishRepository>() with singleton { WishRepositoryImpl(instance(), instance(), instance(), instance()) }

    bind() from singleton { GetWishesListUseCase(instance()) }

    bind() from singleton { GetWishesSettingsUseCase(instance()) }

    bind() from singleton { SetWishesSettingsUseCase(instance()) }

}
