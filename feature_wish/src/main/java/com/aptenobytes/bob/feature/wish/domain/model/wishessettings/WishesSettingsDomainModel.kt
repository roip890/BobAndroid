package com.aptenobytes.bob.feature.wish.domain.model.wishsettings

import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.filter.WishFilterDomainModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WishSettingsDomainModel(
    val filter: WishFilterDomainModel? = null,
    val sort: WishSortType? = null
)
