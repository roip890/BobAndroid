package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.recyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.R
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.feature.profile.databinding.FragmentProfilePictureMenuItemBinding
import com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.ProfilePictureMenuItem

class ProfilePictureMenuItemViewHolder(private val context: Context, val binding: FragmentProfilePictureMenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(profilePictureMenuItem: ProfilePictureMenuItemViewModel) {
        binding.viewModel = profilePictureMenuItem
        binding.lifecycleOwner?.let { lifecycleOwner ->
            profilePictureMenuItem.profilePictureMenuItem.observe(lifecycleOwner, Observer {
                updateProfilePictureMenuItem(profilePictureMenuItem = it)
            })
        }
        updateProfilePictureMenuItem(profilePictureMenuItem = profilePictureMenuItem.profilePictureMenuItem.value)
    }

    private fun updateProfilePictureMenuItem(profilePictureMenuItem: ProfilePictureMenuItem?) {
        profilePictureMenuItem?.let {
            updateTitle(title = profilePictureMenuItem.title)
            updateIcon(icon = profilePictureMenuItem.icon)
        }
    }

    private fun updateTitle(title: String) {
        binding.viewModel?.itemTitle?.postValue(title)
    }

    private fun updateIcon(icon: Drawable) {
        binding.profilePictureMenuItemIcon.load(icon) {
            crossfade(true)
            placeholder(R.drawable.ic_image)
            error(R.drawable.ic_image)
            transformations(RoundedCornersTransformation(10F))
        }
    }

}
