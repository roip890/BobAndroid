package com.aptenobytes.bob.feature.wish.data.network.model

import com.aptenobytes.bob.app.data.network.datasource.toDepartmentDomainModel
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.aptenobytes.bob.feature.wish.data.network.enums.WishStatusNetworkDataType
import com.aptenobytes.bob.feature.wish.data.network.enums.toDomainEnum
import com.aptenobytes.bob.feature.wish.data.network.model.guest.GuestNetworkDataModel
import com.aptenobytes.bob.feature.wish.data.network.model.guest.toDomainModel
import com.aptenobytes.bob.feature.wish.data.network.model.user.UserNetworkDataModel
import com.aptenobytes.bob.feature.wish.data.network.model.user.toDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class WishNetworkDataModel(
    @field:Json(name = "wishId")
    val id: Long,

    @field:Json(name = "guest")
    val guest: GuestNetworkDataModel?,
    @field:Json(name = "userAssigned")
    val user: UserNetworkDataModel?,
    @field:Json(name = "bookingId")
    val bookingId: Long?,

    @field:Json(name = "wishDetails")
    val details: String?,
    @field:Json(name = "wishType")
    val type: String?,
    @field:Json(name = "insertTs")
    val timeStamp: String?,
    @field:Json(name = "wishIconUrl")
    val iconUrl: String?,
    @field:Json(name = "wishStatus")
    val status: WishStatusNetworkDataType?,
    @field:Json(name = "isFavorite")
    val isFavorite: Boolean?,

    @SingleToArray
    @field:Json(name = "departments")
    val departments: List<String>?,
    @SingleToArray
    @field:Json(name = "elements")
    val elements: List<WishElementNetworkDataModel>?
)

internal fun WishNetworkDataModel.toDomainModel(): WishDomainModel {
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
        bookingId = this.bookingId ?: 0,

        details = this.details ?: "",
        type = this.type ?: "",
        timeStamp = this.timeStamp ?: "",
        iconUrl = this.iconUrl ?: "",
        status = status,
        isFavorite = this.isFavorite ?: false,

        departments = departments,
        elements = elements ?: listOf()
    )
}
