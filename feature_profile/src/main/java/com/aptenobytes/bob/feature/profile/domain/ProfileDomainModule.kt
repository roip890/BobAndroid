package com.aptenobytes.bob.feature.profile.domain

import com.aptenobytes.bob.feature.profile.FEATURE_NAME
import com.aptenobytes.bob.feature.profile.domain.repository.ProfileRepository
import com.aptenobytes.bob.feature.profile.domain.repository.ProfileRepositoryImpl
import com.aptenobytes.bob.feature.profile.domain.usecase.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val domainModule = Kodein.Module("${FEATURE_NAME}DomainModule") {

    bind<ProfileRepository>() with singleton { ProfileRepositoryImpl(instance(), instance(), instance(), instance()) }

    bind() from singleton { GetUserBySessionUseCase(instance(), instance()) }

    bind() from singleton { UpdateUserBySessionUseCase(instance(), instance()) }

    bind() from singleton { SetUserStatusUseCase(instance(), instance()) }

    bind() from singleton { UploadProfilePictureBySessionUseCase(instance(), instance()) }

    bind() from singleton { RemoveProfilePictureBySessionUseCase(instance(), instance()) }

}
