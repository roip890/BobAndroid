package com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.library.base.presentation.extension.setOnDebouncedClickListener
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.adapter

fun wishStatusAdapter(
    lifecycleOwner: LifecycleOwner,
    onDebouncedClickListener: ((status: WishStatusType?) -> Unit)? = null
    ): RecyclerViewAdapter<WishStatusViewModel, WishStatusViewHolder> {
    return adapter<WishStatusViewModel, WishStatusViewHolder>(
        items = arrayListOf(),
        withGetItemCount = { adapter -> adapter.items.size },
        withGetItemViewType = { _, _ -> 0 },
        withOnCreateViewHolder = { _, parent, _ ->
            val inflater = LayoutInflater.from(parent.context)
            return@adapter WishStatusViewHolder(
                parent.context,
                DataBindingUtil.inflate(inflater, R.layout.fragment_set_wish_status_item, parent, false)
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
