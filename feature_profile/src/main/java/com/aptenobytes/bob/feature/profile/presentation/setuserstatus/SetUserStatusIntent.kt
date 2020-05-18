package com.aptenobytes.bob.feature.profile.presentation.setuserstatus

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class SetUserStatusIntent : BaseIntent {
    class SetStatusIntent(val userId: Long, val status: UserStatusType) : SetUserStatusIntent()
}
