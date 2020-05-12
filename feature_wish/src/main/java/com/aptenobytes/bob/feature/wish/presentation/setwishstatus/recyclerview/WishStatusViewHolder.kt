package com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.aptenobytes.bob.R
import com.aptenobytes.bob.feature.wish.databinding.FragmentSetWishStatusItemBinding
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType


class WishStatusViewHolder(val context: Context, val binding: FragmentSetWishStatusItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wishStatus: WishStatusViewModel) {
        binding.viewModel = wishStatus
        binding.lifecycleOwner?.let { lifecycleOwner ->
            wishStatus.status.observe(lifecycleOwner, Observer {status ->
                updateStatus(wishStatus = wishStatus, status = status)
            })
        }
        updateStatus(wishStatus = wishStatus, status = wishStatus.status.value)
    }

    private fun updateStatus(wishStatus: WishStatusViewModel, status: WishStatusType?) {
        status?.let {
            wishStatus.statusString.postValue(
                wishStatusTypeToString(
                    status
                )
            )
            wishStatus.statusIcon.postValue(
                wishStatusTypeToIcon(
                    status
                )
            )
        }
    }

    private fun wishStatusTypeToString(status: WishStatusType): String {
        return when(status) {
            WishStatusType.WAITING -> "Wating"
            WishStatusType.PENDING -> "Pending"
            WishStatusType.IN_PROGRESS -> "In Progress"
            WishStatusType.DONE -> "Done"
            else -> ""
        }
    }

    private fun wishStatusTypeToIcon(status: WishStatusType): Drawable {
        return GradientDrawable().apply {
            this.shape = GradientDrawable.OVAL
            this.color = when(status) {
                WishStatusType.WAITING -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_red))
                WishStatusType.PENDING -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_orange))
                WishStatusType.IN_PROGRESS -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_yellow))
                WishStatusType.DONE -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_green))
                else -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.faded_blue))
            }
        }
    }

}
