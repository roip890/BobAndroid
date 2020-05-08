package com.aptenobytes.bob.feature.wish.domain.model.wishessettings.filter

import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.UserType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType

data class WishFilterDomainModel(

    val wishId: Long?,

    val userId: Long?,
    val guestId: Long?,
    val bookingId: Long?,

    val type: String?,
    val minTimestamp: String?,
    val maxTimestamp: String?,
    val statuses: ArrayList<WishStatusType>?,
    val departments: ArrayList<String>?

)
