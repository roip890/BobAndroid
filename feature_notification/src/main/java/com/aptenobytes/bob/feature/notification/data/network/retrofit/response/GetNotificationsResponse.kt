package com.aptenobytes.bob.feature.notification.data.network.retrofit.response

import com.aptenobytes.bob.app.data.network.retrofit.response.AppStatusResponse
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.aptenobytes.bob.feature.notification.data.network.model.NotificationNetworkDataModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class GetNotificationsResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @SingleToArray
    @field:Json(name = "notifications")
    val notifications: List<NotificationNetworkDataModel>?

)

@JsonClass(generateAdapter = true)
internal data class GetNotificationsResponseWrapper(
    @field:Json(name = "response")
    val response: GetNotificationsResponse?
)
