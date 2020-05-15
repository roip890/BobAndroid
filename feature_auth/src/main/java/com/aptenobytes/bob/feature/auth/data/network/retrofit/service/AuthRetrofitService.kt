package com.aptenobytes.bob.feature.auth.data.network.retrofit.service

import com.aptenobytes.bob.feature.auth.data.network.retrofit.request.EmailLoginRequestWrapper
import com.aptenobytes.bob.feature.auth.data.network.retrofit.response.EmailLoginResponseWrapper
import retrofit2.http.*

internal interface AuthRetrofitService {
    @POST("./UserManagement/services/weblogin/login")
    suspend fun emailLoginAsync(
        @Body emailLoginRequestWrapper: EmailLoginRequestWrapper
    ): EmailLoginResponseWrapper?
}
