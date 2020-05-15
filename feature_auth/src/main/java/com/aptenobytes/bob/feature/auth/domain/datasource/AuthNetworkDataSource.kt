package com.aptenobytes.bob.feature.auth.domain.datasource

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel


interface AuthNetworkDataSource {

    suspend fun emailLogin(
        user: UserDomainModel?
    ): UserDomainModel?

}
