package com.aptenobytes.bob.feature.wish.data.db.model

import com.aptenobytes.bob.feature.wish.domain.model.wishelement.WishElementDomainModel
import com.squareup.moshi.Json

data class WishElementRoomDataModel(
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

fun WishElementRoomDataModel.toDomainModel() =
    WishElementDomainModel(
        id = this.id,

        key = this.key,
        value = this.value,
        type = this.type,
        order = this.order
    )
