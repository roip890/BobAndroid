package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview

import android.graphics.drawable.Drawable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel

class WishViewModel(
    val id: MutableLiveData<Long> = MutableLiveData<Long>(),
    val type: MutableLiveData<String> = MutableLiveData<String>(),
    val timeStamp: MutableLiveData<String> = MutableLiveData<String>(),
    val iconUrl: MutableLiveData<String> = MutableLiveData<String>(),
    val status: MutableLiveData<WishStatusType> = MutableLiveData<WishStatusType>(),
    val departments: MutableLiveData<List<DepartmentDomainModel>> = MutableLiveData<List<DepartmentDomainModel>>()
) {

    val statusString = MutableLiveData<String>()
    val statusIcon = MutableLiveData<Drawable>()
    val departmentsString = MutableLiveData<String>()

    init {
    }
}

fun WishDomainModel.toViewModel(): WishViewModel {
    return WishViewModel(
        id = MutableLiveData<Long>(this.id),
        type = MutableLiveData<String>(this.type),
        timeStamp = MutableLiveData<String>(this.timeStamp),
        iconUrl = MutableLiveData<String>(this.iconUrl),
        status = MutableLiveData<WishStatusType>(this.status),
        departments = MutableLiveData<List<DepartmentDomainModel>>(this.departments)
    )
}

fun WishViewModel.toDomainModel(): WishDomainModel {
    return WishDomainModel(
        id = this.id.value ?: 0,
        type = this.type.value,
        timeStamp = this.timeStamp.value,
        iconUrl = this.iconUrl.value,
        status = this.status.value,
        departments = this.departments.value
    )
}
