package com.aptenobytes.bob.feature.profile.presentation.profilepage

import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class ProfilePageIntent : BaseIntent {
    object GetUserBySessionIntent : ProfilePageIntent()
}
