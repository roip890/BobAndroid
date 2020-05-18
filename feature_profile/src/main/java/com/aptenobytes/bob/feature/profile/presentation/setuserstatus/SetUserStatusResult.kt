package com.aptenobytes.bob.feature.profile.presentation.setuserstatus

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class SetUserStatusResult : BaseResult {
    sealed class SetStatusResult : SetUserStatusResult() {
        object Loading : SetStatusResult()
        data class Success(val user: UserDomainModel?) : SetStatusResult()
        data class Failure(val error: Throwable) : SetStatusResult()
    }
}
