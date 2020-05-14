package com.aptenobytes.bob.app.domain.usecase

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository

class EmailLoginUseCase(
    private val appRepository: AppRepository
) {
    suspend fun execute(user: UserDomainModel?): UserDomainModel? {
        return appRepository.setUserSession(
            user = appRepository.emailLogin(user = user)
        )
    }
}
