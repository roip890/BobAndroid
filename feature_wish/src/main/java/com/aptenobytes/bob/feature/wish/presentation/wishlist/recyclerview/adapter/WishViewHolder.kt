package com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview.adapter

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.databinding.FragmentWishListItemBinding


class WishViewHolder(val binding: FragmentWishListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val iconUrlObserver = Observer<String> {
        binding.wishImage.load(it) {
            crossfade(true)
            error(R.drawable.ic_image)
            transformations(RoundedCornersTransformation(10F))
        }
    }

    fun bind(wish: WishViewModel) {
        binding.viewModel = wish
        binding.lifecycleOwner?.let { lifecycleOwner ->
            wish.iconUrl.observe(lifecycleOwner, iconUrlObserver)
        }
    }

}
