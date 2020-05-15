package com.aptenobytes.bob.feature.profile.presentation.profilepage

import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class ProfilePageAction : BaseAction {
    object GetUserBySessionAction : ProfilePageAction()
}
