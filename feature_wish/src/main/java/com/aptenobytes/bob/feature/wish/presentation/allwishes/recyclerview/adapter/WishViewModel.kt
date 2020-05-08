package com.aptenobytes.bob.feature.wish.presentation.allwishes.recyclerview.adapter

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel

class WishViewModel(
    val id: MutableLiveData<Long> = MutableLiveData<Long>(),
    val type: MutableLiveData<String> = MutableLiveData<String>(),
    val timeStamp: MutableLiveData<String> = MutableLiveData<String>(),
    val iconUrl: MutableLiveData<String> = MutableLiveData<String>(),
    val status: MutableLiveData<WishStatusType> = MutableLiveData<WishStatusType>(),
    val departments: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
) {

    val statusString = MediatorLiveData<String>()
    val departmentsString = MediatorLiveData<String>()

    init {
        statusString.addSource(status, Observer {
            statusString.postValue(WishViewModel.wishStatusTypeToString(it))
        })
        departmentsString.addSource(departments, Observer {
            departmentsString.postValue(WishViewModel.wishDepartmentsToString(it))
        })
    }

    companion object {
        @JvmStatic fun wishStatusTypeToString(status: WishStatusType): String {
            return when(status) {
                WishStatusType.WAITING -> "Wating"
                WishStatusType.PENDING -> "Pending"
                WishStatusType.IN_PROGRESS -> "In Progress"
                WishStatusType.DONE -> "Done"
                else -> ""
            }
        }

        @JvmStatic fun wishDepartmentsToString(departments: List<String>): String {
            return departments.joinToString(separator = ", ")
        }
    }
}

fun WishDomainModel.toViewModel(): WishViewModel {
    return WishViewModel(
        id = MutableLiveData<Long>(this.id),
        type = MutableLiveData<String>(this.type),
        timeStamp = MutableLiveData<String>(this.timeStamp),
        iconUrl = MutableLiveData<String>(this.iconUrl),
        status = MutableLiveData<WishStatusType>(this.status),
        departments = MutableLiveData<List<String>>(this.departments)
    )
}
