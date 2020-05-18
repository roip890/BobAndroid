package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview

import android.content.Context
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.databinding.FragmentWishListItemBinding
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.presentation.utils.wishDepartmentsToString
import com.aptenobytes.bob.feature.wish.presentation.utils.wishStatusTypeToIcon
import com.aptenobytes.bob.feature.wish.presentation.utils.wishStatusTypeToString
import com.aptenobytes.bob.feature.wish.presentation.utils.wishTimeStampToString
import java.util.*

class WishItemViewHolder(val context: Context, val binding: FragmentWishListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wish: WishItemViewModel) {
        binding.viewModel = wish
        binding.lifecycleOwner?.let { lifecycleOwner ->
            wish.wishLiveDate.observe(lifecycleOwner, Observer<WishDomainModel?> { wish ->
                updateWish(wish = wish)

            })
            updateWish(wish = wish.wishLiveDate.value)
        }
    }

    private fun updateWish(wish: WishDomainModel?) {
        updateType(type = wish?.type)
        updateIcon(iconUrl = wish?.iconUrl)
        updateStatus(status = wish?.status)
        updateDepartments(departments = wish?.departments)
        updateTimeStamp(timeStamp = wish?.timeStamp)
    }

    private fun updateType(type: String?) {
        type?.let {
            binding.viewModel?.typeString?.postValue(type)
        }
    }

    private fun updateIcon(iconUrl: String?) {
        try {
            iconUrl?.let {
                binding.wishImage.load(iconUrl) {
                    crossfade(true)
                    placeholder(com.aptenobytes.bob.R.drawable.ic_round_room_service_avatar)
                    error(com.aptenobytes.bob.R.drawable.ic_round_room_service_avatar)
                    transformations(RoundedCornersTransformation(10F))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateStatus(status: WishStatusType?) {
        status?.let {
            binding.viewModel?.statusString?.postValue(
                wishStatusTypeToString(
                    status
                )
            )
            binding.viewModel?.statusIcon?.postValue(
                wishStatusTypeToIcon(
                    status,
                    context
                )
            )
        }
    }

    private fun updateDepartments(departments: List<DepartmentDomainModel>?) {
        departments?.let {
            binding.viewModel?.departmentsString?.postValue(
                wishDepartmentsToString(
                    departments
                )
            )
        }
    }

    private fun updateTimeStamp(timeStamp: Date?) {
        timeStamp?.let {
            binding.viewModel?.timeStampString?.postValue(
                wishTimeStampToString(
                    timeStamp
                )
            )
        }
    }


}
