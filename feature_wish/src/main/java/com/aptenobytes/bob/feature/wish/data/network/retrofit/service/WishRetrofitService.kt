package com.aptenobytes.bob.feature.wish.data.network.retrofit.service

import com.aptenobytes.bob.feature.wish.data.network.retrofit.request.SetWishStatusRequest
import com.aptenobytes.bob.feature.wish.data.network.retrofit.response.GetWishesResponseWrapper
import com.aptenobytes.bob.feature.wish.data.network.retrofit.response.SetWishStatusResponseWrapper
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface WishRetrofitService {
    @GET("./WishManagement/services/wishes/getPage?")
    suspend fun getWishesAsync(

        @Query("id") wishId: Long? = null,

        @Query("user") userId: Long? = null,
        @Query("guest") guestId: Long? = null,
        @Query("booking") bookingId: Long? = null,

        @Query("minTs") minTimestamp: String? = null,
        @Query("maxTs") maxTimestamp: String? = null,
        @Query("status") statuses: ArrayList<String>? = null,
        @Query("departments") departments: ArrayList<String>? = null,

        @Query("sort") sort: String? = null,
        @Query("index") index: Int? = 0,
        @Query("limit") limit: Int? = 20

    ): GetWishesResponseWrapper?

    @POST("./WishManagement/services/wishes/update")
    suspend fun setWishStatusAsync(
        @Field("request") setStatusRequest: SetWishStatusRequest
    ): SetWishStatusResponseWrapper?
}
