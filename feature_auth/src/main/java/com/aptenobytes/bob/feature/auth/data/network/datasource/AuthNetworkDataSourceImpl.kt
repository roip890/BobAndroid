package com.aptenobytes.bob.feature.auth.data.network.datasource

import com.aptenobytes.bob.app.data.network.model.user.toNetworkModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.auth.data.network.retrofit.request.EmailLoginRequest
import com.aptenobytes.bob.feature.auth.data.network.retrofit.request.EmailLoginRequestWrapper
import com.aptenobytes.bob.feature.auth.data.network.retrofit.service.AuthRetrofitService
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthNetworkDataSource

internal class AuthNetworkDataSourceImpl(
    private val authRetrofitService: AuthRetrofitService
) : AuthNetworkDataSource {

    override suspend fun emailLogin(
        user: UserDomainModel?
    ) = authRetrofitService.emailLoginAsync(
        emailLoginRequestWrapper = EmailLoginRequestWrapper(
            request = EmailLoginRequest(
                user = user?.toNetworkModel()
            )
        )
    )
        ?.response
        ?.user
        ?.toDomainModel()

}
