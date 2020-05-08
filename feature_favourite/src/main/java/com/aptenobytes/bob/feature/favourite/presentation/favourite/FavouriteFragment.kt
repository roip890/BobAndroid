package com.aptenobytes.bob.feature.favourite.presentation.favourite

import com.aptenobytes.bob.feature.favourite.R
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouriteFragment : BaseContainerFragment() {

    override val layoutResourceId = R.layout.fragment_favourites

    override fun onResume() {
        super.onResume()
        underConstructionAnimation.playAnimation()
    }
}
