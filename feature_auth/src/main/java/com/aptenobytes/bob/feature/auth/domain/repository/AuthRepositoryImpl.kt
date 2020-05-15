package com.aptenobytes.bob.feature.auth.domain.repository

import com.aptenobytes.bob.feature.auth.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthCacheDataSource
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthLocalDataSource
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthNetworkDataSource
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthSharedPreferencesDataSource

class AuthRepositoryImpl(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    private val authCacheDataSource: AuthCacheDataSource,
    private val authSharedPreferencesDataSource: AuthSharedPreferencesDataSource
) : AuthRepository {

    // login
    override suspend fun emailLogin(
        user: UserDomainModel?
    ): UserDomainModel? {
        return authNetworkDataSource.emailLogin(
            user = user
        )
    }


}
