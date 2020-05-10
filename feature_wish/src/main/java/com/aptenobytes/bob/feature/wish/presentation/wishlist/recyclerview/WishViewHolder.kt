package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.databinding.FragmentWishListItemBinding
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType

class WishViewHolder(val context: Context, val binding: FragmentWishListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val iconUrlObserver = Observer<String?> { iconUrl ->
        updateIcon(iconUrl = iconUrl)
    }

    fun bind(wish: WishViewModel) {
        binding.viewModel = wish
        binding.lifecycleOwner?.let { lifecycleOwner ->
            wish.iconUrl.observe(lifecycleOwner, iconUrlObserver)
            wish.status.observe(lifecycleOwner, Observer {status ->
                updateStatus(wish = wish, status = status)
            })
            wish.departments.observe(lifecycleOwner, Observer {departments ->
                updateDepartments(wish = wish, departments = departments)
            })
        }
        updateIcon(iconUrl = wish.iconUrl.value)
        updateStatus(wish = wish, status = wish.status.value)
        updateDepartments(wish = wish, departments = wish.departments.value)
    }

    private fun updateIcon(iconUrl: String?) {
        try {
            iconUrl?.let {
                binding.wishImage.load(iconUrl) {
                    crossfade(true)
                    error(R.drawable.ic_image)
                    transformations(RoundedCornersTransformation(10F))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateStatus(wish: WishViewModel, status: WishStatusType?) {
        status?.let {
            wish.statusString.postValue(
                wishStatusTypeToString(
                    status
                )
            )
            wish.statusIcon.postValue(
                wishStatusTypeToIcon(
                    status
                )
            )
        }
    }

    private fun updateDepartments(wish: WishViewModel, departments: List<DepartmentDomainModel>?) {
        departments?.let {
            wish.departmentsString.postValue(
                wishDepartmentsToString(
                    departments
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
                WishStatusType.WAITING -> ColorStateList.valueOf(ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_red))
                WishStatusType.PENDING -> ColorStateList.valueOf(ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_orange))
                WishStatusType.IN_PROGRESS -> ColorStateList.valueOf(ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_yellow))
                WishStatusType.DONE -> ColorStateList.valueOf(ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_green))
                else -> ColorStateList.valueOf(ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_blue))
            }
        }
    }

    private fun wishDepartmentsToString(departments: List<DepartmentDomainModel>): String {
        return departments.joinToString(separator = ", ") { it.name }
    }

}
