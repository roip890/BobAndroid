package com.aptenobytes.bob.feature.auth.domain

import com.aptenobytes.bob.feature.auth.FEATURE_NAME
import com.aptenobytes.bob.feature.auth.domain.repository.AuthRepository
import com.aptenobytes.bob.feature.auth.domain.repository.AuthRepositoryImpl
import com.aptenobytes.bob.feature.auth.domain.usecase.EmailLoginUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val domainModule = Kodein.Module("${FEATURE_NAME}DomainModule") {

    bind<AuthRepository>() with singleton { AuthRepositoryImpl(instance(), instance(), instance(), instance()) }

    bind() from singleton { EmailLoginUseCase(instance(), instance()) }

}
