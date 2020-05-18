package com.aptenobytes.bob.feature.profile.presentation.setuserstatus.recyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.aptenobytes.bob.R
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.feature.profile.databinding.FragmentSetUserStatusItemBinding

class UserStatusViewHolder(private val context: Context, val binding: FragmentSetUserStatusItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userStatus: UserStatusViewModel) {
        binding.viewModel = userStatus
        binding.lifecycleOwner?.let { lifecycleOwner ->
            userStatus.status.observe(lifecycleOwner, Observer {status ->
                updateStatus(userStatus = userStatus, status = status)
            })
        }
        updateStatus(userStatus = userStatus, status = userStatus.status.value)
    }

    private fun updateStatus(userStatus: UserStatusViewModel, status: UserStatusType?) {
        status?.let {
            userStatus.statusString.postValue(
                userStatusTypeToString(
                    status
                )
            )
            userStatus.statusIcon.postValue(
                userStatusTypeToIcon(
                    status
                )
            )
        }
    }

    private fun userStatusTypeToString(status: UserStatusType): String {
        return when(status) {
            UserStatusType.ACTIVE -> "Active"
            UserStatusType.INACTIVE -> "Inactive"
            else -> ""
        }
    }

    private fun userStatusTypeToIcon(status: UserStatusType): Drawable {
        return GradientDrawable().apply {
            this.shape = GradientDrawable.OVAL
            this.color = when(status) {
                UserStatusType.INACTIVE -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_red))
                UserStatusType.ACTIVE -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_green))
                else -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_blue))
            }
        }
    }

}
