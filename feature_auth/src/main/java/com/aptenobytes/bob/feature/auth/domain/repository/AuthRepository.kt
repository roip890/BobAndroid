package com.aptenobytes.bob.feature.auth.domain.repository

import com.aptenobytes.bob.feature.auth.domain.model.user.UserDomainModel

interface AuthRepository {

    // login
    suspend fun emailLogin(
        user: UserDomainModel?
    ): UserDomainModel?

}
