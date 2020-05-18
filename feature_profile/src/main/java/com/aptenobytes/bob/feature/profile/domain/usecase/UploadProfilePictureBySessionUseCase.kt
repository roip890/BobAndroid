package com.aptenobytes.bob.feature.profile.domain.usecase

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.feature.profile.data.network.constants.PROFILE_PICTURE_PREFIX
import com.aptenobytes.bob.feature.profile.domain.repository.ProfileRepository

class UploadProfilePictureBySessionUseCase(
    private val appRepository: AppRepository,
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(imagePath: String): String? {
        val user = appRepository.getUserSession()
        user?.let {
            val profilePictureUrl: String? =  profileRepository.uploadProfilePicture(
                userId = user.id,
                imagePath = imagePath
            )
            profilePictureUrl?.let {
                val updatedUser = profileRepository.updateUser(
                    user = UserDomainModel(
                        id = user.id,
                        imageUrl = "${PROFILE_PICTURE_PREFIX}${profilePictureUrl}"
                    )
                )
                return updatedUser?.imageUrl
            }
        }
        return null
    }
}
