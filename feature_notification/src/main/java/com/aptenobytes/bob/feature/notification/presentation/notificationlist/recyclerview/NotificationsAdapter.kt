package com.aptenobytes.bob.feature.notification.presentation.notificationlist.recyclerview

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.aptenobytes.bob.feature.notification.R
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import com.aptenobytes.bob.library.base.presentation.extension.setOnDebouncedClickListener
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.adapter.BasicLoadingViewHolder
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.adapter.RecyclerViewLoadMoreAdapter
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.adapter.loadMoreAdapter


fun notificationsAdapter(
    lifecycleOwner: LifecycleOwner,
    onDebouncedClickListener: ((status: NotificationDomainModel?) -> Unit)? = null
): RecyclerViewLoadMoreAdapter<NotificationViewModel, NotificationViewHolder> {
    return loadMoreAdapter<NotificationViewModel, NotificationViewHolder>(
        items = arrayListOf(),
        withGetItemCount = { adapter -> adapter.items.size },
        withGetItemViewType = { _, _ -> 0 },
        withOnCreateViewHolder = { _, parent, _ ->
            val inflater = LayoutInflater.from(parent.context)
            NotificationViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.notification_item, parent, false)).apply {
                binding.lifecycleOwner = lifecycleOwner
            }
        },
        withOnCreateLoadingViewHolder = {
                _, parent, _ ->
            val view = LayoutInflater.from(parent.context).inflate(com.aptenobytes.bob.library.base.R.layout.progress_loading, parent, false)
            BasicLoadingViewHolder(view)
        },
        withOnBindViewHolder = { adapter, holder, position ->
            holder.itemView.setOnDebouncedClickListener { onDebouncedClickListener?.invoke(adapter.items[position]?.toDomainModel()) }
            adapter.items[position]?.let { holder.bind(it) }
        }
    )
}
