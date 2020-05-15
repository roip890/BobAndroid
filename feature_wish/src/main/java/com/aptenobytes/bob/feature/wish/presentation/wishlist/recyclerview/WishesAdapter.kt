package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.extension.setOnDebouncedClickListener
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.adapter.BasicLoadingViewHolder
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.adapter.RecyclerViewLoadMoreAdapter
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.adapter.loadMoreAdapter


fun wishesAdapter(
    lifecycleOwner: LifecycleOwner,
    onDebouncedClickListener: ((status: WishDomainModel?) -> Unit)? = null
): RecyclerViewLoadMoreAdapter<WishItemViewModel, WishItemViewHolder> {
    return loadMoreAdapter<WishItemViewModel, WishItemViewHolder>(
        items = arrayListOf(),
        withGetItemCount = { adapter -> adapter.items.size },
        withGetItemViewType = { _, _ -> 0 },
        withOnCreateViewHolder = { _, parent, _ ->
            val inflater = LayoutInflater.from(parent.context)
            WishItemViewHolder(
                parent.context,
                DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list_item, parent, false)).apply {
                binding.lifecycleOwner = lifecycleOwner
            }
        },
        withOnCreateLoadingViewHolder = {
                _, parent, _ ->
            val view = LayoutInflater.from(parent.context).inflate(com.aptenobytes.bob.library.base.R.layout.progress_loading, parent, false)
            BasicLoadingViewHolder(view)
        },
        withOnBindViewHolder = { adapter, holder, position ->
            holder.itemView.setOnDebouncedClickListener { onDebouncedClickListener?.invoke(adapter.items[position]?.wishLiveDate?.value) }
            adapter.items[position]?.let { holder.bind(it) }
        }
    )
}
