package com.aptenobytes.bob.feature.profile.data.network.retrofit.service

import com.aptenobytes.bob.feature.profile.data.network.retrofit.request.UpdateUserRequestWrapper
import com.aptenobytes.bob.feature.profile.data.network.retrofit.response.GetUsersResponseWrapper
import com.aptenobytes.bob.feature.profile.data.network.retrofit.response.UpdateUserResponseWrapper
import retrofit2.http.*

internal interface ProfileRetrofitService {

    @GET("./UserManagement/services/users/getPage?")
    suspend fun getUsersAsync(

        @Query("id") userId: Long? = null,

        @Query("hotel") hotelId: Long? = null,
        @Query("email") email: String? = null,

        @Query("firstname") firstName: String? = null,
        @Query("lastname") lastName: String? = null,
        @Query("status") statuses: ArrayList<String>? = null,
        @Query("departments") departments: ArrayList<String>? = null,

        @Query("sort") sort: String? = null,
        @Query("index") index: Int? = 0,
        @Query("limit") limit: Int? = 20

    ): GetUsersResponseWrapper?

    @POST("./UserManagement/services/users/update")
    suspend fun updateUserAsync(
        @Body updateUserRequestWrapper: UpdateUserRequestWrapper
    ): UpdateUserResponseWrapper?

}
