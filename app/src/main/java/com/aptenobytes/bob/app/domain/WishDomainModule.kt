package com.aptenobytes.bob.app.domain

import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.app.domain.repository.AppRepositoryImpl
import com.aptenobytes.bob.app.domain.usecase.EmailLoginUseCase
import com.aptenobytes.bob.app.domain.usecase.GetDepartmentsListUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val domainModule = Kodein.Module("AppDomainModule") {

    bind<AppRepository>() with singleton { AppRepositoryImpl(instance(), instance(), instance(), instance()) }

    bind() from singleton { GetDepartmentsListUseCase(instance()) }

    bind() from singleton { EmailLoginUseCase(instance()) }

}
