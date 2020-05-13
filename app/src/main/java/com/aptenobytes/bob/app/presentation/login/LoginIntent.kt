package com.aptenobytes.bob.app.presentation.login

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class LoginIntent : BaseIntent {
    class EmailLoginIntent(val user: UserDomainModel) : LoginIntent()
}
