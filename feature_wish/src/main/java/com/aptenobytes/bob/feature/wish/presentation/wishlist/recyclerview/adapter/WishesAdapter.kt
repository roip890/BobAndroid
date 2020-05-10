package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview.adapter

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.recyclerview.adapter.adapter

fun wishesAdapter(
    lifecycleOwner: LifecycleOwner
    ): RecyclerViewAdapter<WishViewModel, WishViewHolder> {
    return adapter(
        items = arrayListOf(),
        withGetItemCount = { adapter -> adapter.items.size },
        withGetItemViewType = { _, _ -> 0 },
        withOnCreateViewHolder = { _, parent, _ ->
            val inflater = LayoutInflater.from(parent.context)
            return@adapter WishViewHolder(DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list_item, parent, false)).apply {
                binding.lifecycleOwner = lifecycleOwner
            }
        },
        withOnBindViewHolder = { adapter, holder, position ->
            holder.bind(adapter.items[position])
        }
    )
}
