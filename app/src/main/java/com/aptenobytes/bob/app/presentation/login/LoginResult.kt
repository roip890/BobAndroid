package com.aptenobytes.bob.app.presentation.login

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class LoginResult : BaseResult {
    sealed class EmailLoginResult : LoginResult() {
        object Loading : EmailLoginResult()
        data class Success(val user: UserDomainModel?) : EmailLoginResult()
        data class Failure(val error: Throwable) : EmailLoginResult()
    }
}
