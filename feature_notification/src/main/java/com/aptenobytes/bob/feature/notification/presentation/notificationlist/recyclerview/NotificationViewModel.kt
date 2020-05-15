package com.aptenobytes.bob.feature.notification.presentation.notificationlist.recyclerview

import androidx.lifecycle.MutableLiveData
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import java.util.Date

class NotificationViewModel(
    val id: MutableLiveData<Long> = MutableLiveData<Long>(),
    val content: MutableLiveData<String> = MutableLiveData<String>(),
    val timeStamp: MutableLiveData<Date> = MutableLiveData<Date>(),
    val iconUrl: MutableLiveData<String> = MutableLiveData<String>(),
    val type: MutableLiveData<String> = MutableLiveData<String>(),
    val value: MutableLiveData<String> = MutableLiveData<String>(),
    val isRead: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
) {

    val timeStampString = MutableLiveData<String>()

    init {
    }
}

fun NotificationDomainModel.toViewModel(): NotificationViewModel {
    return NotificationViewModel(
        id = MutableLiveData<Long>(this.id),
        content = MutableLiveData<String>(this.type),
        timeStamp = MutableLiveData<Date>(this.timeStamp),
        iconUrl = MutableLiveData<String>(this.iconUrl),
        type = MutableLiveData<String>(this.type),
        value = MutableLiveData<String>(this.value),
        isRead = MutableLiveData<Boolean>(this.isRead)
    )
}

fun NotificationViewModel.toDomainModel(): NotificationDomainModel {
    return NotificationDomainModel(
        id = this.id.value ?: 0,
        content = this.type.value,
        timeStamp = this.timeStamp.value,
        iconUrl = this.iconUrl.value,
        type = this.type.value,
        value = this.value.value,
        isRead = this.isRead.value
    )
}
