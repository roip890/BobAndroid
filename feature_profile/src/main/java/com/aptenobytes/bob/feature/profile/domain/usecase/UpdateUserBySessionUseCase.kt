package com.aptenobytes.bob.feature.profile.domain.usecase

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.feature.profile.domain.repository.ProfileRepository

class UpdateUserBySessionUseCase(
    private val appRepository: AppRepository,
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(user: UserDomainModel): UserDomainModel? = profileRepository.updateUser(
        user = user
    )
}
