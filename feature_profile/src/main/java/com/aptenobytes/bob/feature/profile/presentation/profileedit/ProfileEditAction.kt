package com.aptenobytes.bob.feature.profile.presentation.profileedit

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class ProfileEditAction : BaseAction {
    object GetUserBySessionAction : ProfileEditAction()
    class UpdateUserBySessionAction(val user: UserDomainModel) : ProfileEditAction()
    class SubmitUserBySessionAction(val user: UserDomainModel) : ProfileEditAction()
}
