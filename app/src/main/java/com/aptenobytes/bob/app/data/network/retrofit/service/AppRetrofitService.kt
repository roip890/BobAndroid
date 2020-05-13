package com.aptenobytes.bob.app.data.network.retrofit.service

import com.aptenobytes.bob.app.data.network.retrofit.request.EmailLoginRequestWrapper
import com.aptenobytes.bob.app.data.network.retrofit.response.EmailLoginResponseWrapper
import com.aptenobytes.bob.app.data.network.retrofit.response.GetDepartmentsResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AppRetrofitService {

    @GET("./WishManagement/services/departments/getnames?")
    suspend fun getDepartmentsAsync(
        @Query("hotelId") hotelId: Long? = null
    ): GetDepartmentsResponseWrapper?

    @POST("./UserManagement/services/weblogin/login")
    suspend fun emailLoginAsync(
        @Body emailLoginRequestWrapper: EmailLoginRequestWrapper
    ): EmailLoginResponseWrapper?

}
