package com.aptenobytes.bob.feature.notification.data.network.retrofit.service

import com.aptenobytes.bob.feature.notification.data.network.retrofit.response.GetNotificationsResponseWrapper
import retrofit2.http.*

internal interface NotificationRetrofitService {
    @GET("./NotificationServices/services/user/notifications/getPage?")
    suspend fun getNotificationsAsync(

        @Query("notificationId") notificationId: Long? = null,

        @Query("userId") userId: Long? = null,
        @Query("guestId") guestId: Long? = null,
        @Query("bookingId") bookingId: Long? = null,

        @Query("sort") sort: String? = null,
        @Query("offset") index: Int? = 0,
        @Query("amount") limit: Int? = 20

    ): GetNotificationsResponseWrapper?

}
