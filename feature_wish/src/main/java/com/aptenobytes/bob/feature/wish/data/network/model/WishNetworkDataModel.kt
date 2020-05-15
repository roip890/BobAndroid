package com.aptenobytes.bob.feature.wish.data.network.model

import com.aptenobytes.bob.app.data.network.datasource.toDepartmentDomainModel
import com.aptenobytes.bob.app.data.network.datasource.toDepartmentNetworkModel
import com.aptenobytes.bob.app.data.network.model.guest.GuestNetworkDataModel
import com.aptenobytes.bob.app.data.network.model.guest.toDomainModel
import com.aptenobytes.bob.app.data.network.model.guest.toNetworkModel
import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.aptenobytes.bob.app.data.network.model.user.toDomainModel
import com.aptenobytes.bob.app.data.network.model.user.toNetworkModel
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.aptenobytes.bob.feature.wish.data.network.constants.WISH_DATE_FORMAT
import com.aptenobytes.bob.feature.wish.data.network.enums.WishStatusNetworkDataType
import com.aptenobytes.bob.feature.wish.data.network.enums.toDomainEnum
import com.aptenobytes.bob.feature.wish.data.network.enums.toNetworkEnum
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class WishNetworkDataModel(
    @field:Json(name = "wishId")
    val id: Long,

    @field:Json(name = "guest")
    val guest: GuestNetworkDataModel? = null,
    @field:Json(name = "userAssigned")
    val user: UserNetworkDataModel? = null,
    @field:Json(name = "bookingId")
    val bookingId: Long? = null,

    @field:Json(name = "wishDetails")
    val details: String? = null,
    @field:Json(name = "wishType")
    val type: String? = null,
    @field:Json(name = "insertTs")
    val timeStamp: String? = null,
    @field:Json(name = "wishIconUrl")
    val iconUrl: String? = null,
    @field:Json(name = "wishStatus")
    val status: WishStatusNetworkDataType? = null,
    @field:Json(name = "isFavorite")
    val isFavorite: Boolean? = null,

    @SingleToArray
    @field:Json(name = "departments")
    val departments: List<String>? = null,
    @SingleToArray
    @field:Json(name = "elements")
    val elements: List<WishElementNetworkDataModel>? = null
)

fun WishNetworkDataModel.toDomainModel(): WishDomainModel {
    val elements = this.elements
        ?.map { it.toDomainModel() }
    val guest = this.guest
        ?.toDomainModel()
    val user = this.user
        ?.toDomainModel()
    val status = this.status
        ?.toDomainEnum()
    val departments = this.departments
        ?.map { it.toDepartmentDomainModel() }

    return WishDomainModel(
        id = this.id,

        guest = guest,
        user = user,
        bookingId = this.bookingId,

        details = this.details,
        type = this.type,
        timeStamp = this.timeStamp?.let { SimpleDateFormat(WISH_DATE_FORMAT, Locale.ENGLISH).parse(this.timeStamp)} ?: run { null },
        iconUrl = this.iconUrl,
        status = status,
        isFavorite = this.isFavorite,

        departments = departments ?: listOf(),
        elements = elements ?: listOf()
    )
}

fun WishDomainModel.toNetworkModel(): WishNetworkDataModel {
    val elements = this.elements
        ?.map { it.toNetworkModel() }
    val guest = this.guest
        ?.toNetworkModel()
    val user = this.user
        ?.toNetworkModel()
    val status = this.status
        ?.toNetworkEnum()
    val departments = this.departments
        ?.map { it.toDepartmentNetworkModel() }

    return WishNetworkDataModel(
        id = this.id,

        guest = guest,
        user = user,
        bookingId = this.bookingId,

        details = this.details,
        type = this.type,
        timeStamp = this.timeStamp?.let { SimpleDateFormat(WISH_DATE_FORMAT, Locale.ENGLISH).format(this.timeStamp)} ?: run { null },
        iconUrl = this.iconUrl,
        status = status,
        isFavorite = this.isFavorite,

        departments = departments ?: listOf(),
        elements = elements ?: listOf()
    )
}
