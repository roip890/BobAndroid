package com.aptenobytes.bob.feature.auth.domain.usecase

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.feature.auth.domain.repository.AuthRepository

class EmailLoginUseCase(
    private val appRepository: AppRepository,
    private val authRepository: AuthRepository
) {
    suspend fun execute(user: UserDomainModel?): UserDomainModel? {
        return appRepository.setUserSession(
            user = authRepository.emailLogin(user = user)
        )
    }
}
