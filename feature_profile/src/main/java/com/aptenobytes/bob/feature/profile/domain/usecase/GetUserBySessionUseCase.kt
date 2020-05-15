package com.aptenobytes.bob.feature.profile.domain.usecase

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.feature.profile.domain.repository.ProfileRepository

class GetUserBySessionUseCase(
    private val appRepository: AppRepository,
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(): UserDomainModel? {
        val user = appRepository.getUserSession()
        return user?.let {
            return profileRepository.getUsers(
                userId = user.id,
                index = 0,
                limit = 1
            )
                .firstOrNull()
        } ?: run {
            null
        }
    }
}
