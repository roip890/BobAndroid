package com.aptenobytes.bob.feature.wish.domain.model.wish

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.guest.GuestDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wishelement.WishElementDomainModel
import java.util.*

data class WishDomainModel(

    val id: Long = 0,

    val guest: GuestDomainModel? = null,
    val user: UserDomainModel? = null,
    val bookingId: Long? = null,

    val details: String? = null,
    val type: String? = null,
    val timeStamp: Date? = null,
    val iconUrl: String? = null,
    var status: WishStatusType? = WishStatusType.UNKNOWN,
    val isFavorite: Boolean? = null,

    val departments: List<DepartmentDomainModel>? = listOf(),
    val elements: List<WishElementDomainModel>? = listOf()

)
