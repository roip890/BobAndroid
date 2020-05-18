package com.aptenobytes.bob.feature.profile.domain.usecase

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.feature.profile.domain.repository.ProfileRepository

class RemoveProfilePictureBySessionUseCase(
    private val appRepository: AppRepository,
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(): String? {
        val user = appRepository.getUserSession()
        user?.let {
            val updatedUser = profileRepository.updateUser(
                user = UserDomainModel(
                    id = user.id,
                    imageUrl = ""
                )
            )
            return updatedUser?.imageUrl
        }
        return null
    }
}
