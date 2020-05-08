package com.aptenobytes.bob.feature.wish.domain.model.wishessettings

import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.filter.WishFilterDomainModel

data class WishesSettingsDomainModel(

    val filter: WishFilterDomainModel? = null,
    val sort: WishSortType? = null

)
