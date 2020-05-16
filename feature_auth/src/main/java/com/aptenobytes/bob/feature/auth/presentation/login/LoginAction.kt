package com.aptenobytes.bob.feature.auth.presentation.login

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class LoginAction : BaseAction {
    class EmailLoginAction(val user: UserDomainModel) : LoginAction()
}
