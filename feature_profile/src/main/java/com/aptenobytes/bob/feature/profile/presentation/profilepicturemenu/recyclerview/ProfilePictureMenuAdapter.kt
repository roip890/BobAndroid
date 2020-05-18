package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.recyclerview

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.feature.profile.R
import com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.ProfilePictureMenuItem
import com.aptenobytes.bob.library.base.presentation.extension.setOnDebouncedClickListener
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.adapter

fun profilePictureMenuAdapter(
    lifecycleOwner: LifecycleOwner,
    onDebouncedClickListener: ((profilePictureMenuItem: ProfilePictureMenuItem?) -> Unit)? = null
    ): RecyclerViewAdapter<ProfilePictureMenuItemViewModel, ProfilePictureMenuItemViewHolder> {
    return adapter<ProfilePictureMenuItemViewModel, ProfilePictureMenuItemViewHolder>(
        items = arrayListOf(),
        withGetItemCount = { adapter -> adapter.items.size },
        withGetItemViewType = { _, _ -> 0 },
        withOnCreateViewHolder = { _, parent, _ ->
            val inflater = LayoutInflater.from(parent.context)
            return@adapter ProfilePictureMenuItemViewHolder(
                parent.context,
                DataBindingUtil.inflate(inflater, R.layout.fragment_profile_picture_menu_item, parent, false)
            ).apply {
                binding.lifecycleOwner = lifecycleOwner
            }
        },
        withOnBindViewHolder = { adapter, holder, position ->
            holder.itemView.setOnDebouncedClickListener { onDebouncedClickListener?.invoke(adapter.items[position]?.toDomainModel()) }
            adapter.items[position]?.let { holder.bind(it) }
        }
    )
}
