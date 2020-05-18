package com.aptenobytes.bob.feature.profile.domain.usecase

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.feature.profile.domain.repository.ProfileRepository

class SetUserStatusUseCase(
    private val appRepository: AppRepository,
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(userId: Long, status: UserStatusType): UserDomainModel? = profileRepository.setUserStatus(
        userId = userId,
        status = status
    )
}
