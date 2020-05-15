package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import java.util.*

class WishItemViewModel(
    val wishLiveDate: MutableLiveData<WishDomainModel> = MutableLiveData<WishDomainModel>()
) {

    val typeString = MutableLiveData<String>()
    val statusString = MutableLiveData<String>()
    val statusIcon = MutableLiveData<Drawable>()
    val statusColor = MutableLiveData<Int>()
    val departmentsString = MutableLiveData<String>()
    val timeStampString = MutableLiveData<String>()

    init {
    }
}
