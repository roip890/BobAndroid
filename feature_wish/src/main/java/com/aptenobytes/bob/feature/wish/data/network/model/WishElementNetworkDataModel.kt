package com.aptenobytes.bob.feature.wish.data.network.model

import com.aptenobytes.bob.feature.wish.domain.model.wishelement.WishElementDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class WishElementNetworkDataModel(
    @field:Json(name = "wishElementId")
    val id: Long = 0,

    @field:Json(name = "elementKey")
    val key: String?,
    @field:Json(name = "elementValue")
    val value: String?,
    @field:Json(name = "elementType")
    val type: String?,
    @field:Json(name = "elementOrder")
    val order: Long?
)

internal fun WishElementNetworkDataModel.toDomainModel() =
    WishElementDomainModel(
        id = this.id,

        key = this.key,
        value = this.value,
        type = this.type,
        order = this.order
    )
