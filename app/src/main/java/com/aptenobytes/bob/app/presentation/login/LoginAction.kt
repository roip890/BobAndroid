package com.aptenobytes.bob.app.presentation.login

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class LoginAction : BaseAction {
    class EmailLoginAction(val user: UserDomainModel) : LoginAction()
}
