package com.aptenobytes.bob.feature.profile.presentation.profile

import com.aptenobytes.bob.feature.profile.R
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseContainerFragment() {

    override val layoutResourceId = R.layout.fragment_profile

    override fun onResume() {
        super.onResume()
        underConstructionAnimation.playAnimation()
    }
}
