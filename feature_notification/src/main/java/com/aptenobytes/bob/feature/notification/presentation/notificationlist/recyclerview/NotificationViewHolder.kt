package com.aptenobytes.bob.feature.notification.presentation.notificationlist.recyclerview

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.feature.notification.R
import com.aptenobytes.bob.feature.notification.data.network.constants.NOTIFICATION_DATE_FORMAT
import com.aptenobytes.bob.feature.notification.databinding.NotificationItemBinding
import java.text.SimpleDateFormat
import java.util.*

class NotificationViewHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(notification: NotificationViewModel) {
        binding.viewModel = notification
        binding.lifecycleOwner?.let { lifecycleOwner ->
            notification.iconUrl.observe(lifecycleOwner, Observer<String?> { iconUrl ->
                updateIcon(iconUrl = iconUrl)
            })
            notification.timeStamp.observe(lifecycleOwner, Observer {timeStamp ->
                updateTimeStamp(notification = notification, timeStamp = timeStamp)
            })
        }
        updateIcon(iconUrl = notification.iconUrl.value)
    }

    private fun updateIcon(iconUrl: String?) {
        try {
            iconUrl?.let {
                binding.notificationImage.load(iconUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                    transformations(RoundedCornersTransformation(10F))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateTimeStamp(notification: NotificationViewModel, timeStamp: Date?) {
        timeStamp?.let {
            notification.timeStampString.postValue(
                notificationTimeStampToString(
                    timeStamp
                )
            )
        }
    }

    private fun notificationTimeStampToString(timeStamp: Date): String? {
        return try {
            timeStamp.let { SimpleDateFormat(NOTIFICATION_DATE_FORMAT, Locale.ENGLISH).format(timeStamp) } ?: run { null }
        } catch (e: Exception) {
            null
        }
    }

}
