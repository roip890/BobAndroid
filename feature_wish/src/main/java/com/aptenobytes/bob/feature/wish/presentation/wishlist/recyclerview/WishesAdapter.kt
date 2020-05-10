package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.toDomainModel
import com.aptenobytes.bob.library.base.presentation.extension.setOnDebouncedClickListener
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.recyclerview.adapter.adapter

fun wishesAdapter(
    lifecycleOwner: LifecycleOwner,
    onDebouncedClickListener: ((status: WishDomainModel?) -> Unit)? = null
): RecyclerViewAdapter<WishViewModel, WishViewHolder> {
    return adapter(
        items = arrayListOf(),
        withGetItemCount = { adapter -> adapter.items.size },
        withGetItemViewType = { _, _ -> 0 },
        withOnCreateViewHolder = { _, parent, _ ->
            val inflater = LayoutInflater.from(parent.context)
            return@adapter WishViewHolder(
                parent.context,
                DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list_item, parent, false)).apply {
                binding.lifecycleOwner = lifecycleOwner
            }
        },
        withOnBindViewHolder = { adapter, holder, position ->
            holder.itemView.setOnDebouncedClickListener { onDebouncedClickListener?.invoke(adapter.items[position].toDomainModel()) }
            holder.bind(adapter.items[position])
        }
    )
}
