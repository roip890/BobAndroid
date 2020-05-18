package com.aptenobytes.bob.feature.profile.presentation.setuserstatus

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class SetUserStatusAction : BaseAction {
    class SetStatusAction(val userId: Long, val status: UserStatusType) : SetUserStatusAction()
}
