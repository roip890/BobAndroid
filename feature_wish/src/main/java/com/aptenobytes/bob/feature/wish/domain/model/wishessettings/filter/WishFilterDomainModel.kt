package com.aptenobytes.bob.feature.wish.domain.model.wishsettings.filter

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.squareup.moshi.JsonClass
import java.util.*
import kotlin.collections.ArrayList

@JsonClass(generateAdapter = true)
data class WishFilterDomainModel(

    val wishId: Long? = null,

    val userId: Long? = null,
    val guestId: Long? = null,
    val bookingId: Long? = null,

    val type: String? = null,
    val minTimestamp: Date? = null,
    val maxTimestamp: Date? = null,
    val statuses: ArrayList<WishStatusType>? = null,
    val departments: ArrayList<DepartmentDomainModel>? = null

)
