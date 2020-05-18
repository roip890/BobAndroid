package com.aptenobytes.bob.feature.profile.presentation.profileedit

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class ProfileEditResult : BaseResult {
    sealed class GetUserBySessionResult : ProfileEditResult() {
        object Loading : GetUserBySessionResult()
        data class Success(val user: UserDomainModel?) : GetUserBySessionResult()
        data class Failure(val error: Throwable) : GetUserBySessionResult()
    }
    sealed class UpdateUserBySessionResult : ProfileEditResult() {
        object Loading : UpdateUserBySessionResult()
        data class Success(val user: UserDomainModel?) : UpdateUserBySessionResult()
        data class Failure(val error: Throwable) : UpdateUserBySessionResult()
    }
    sealed class SubmitUserBySessionResult : ProfileEditResult() {
        object Loading : SubmitUserBySessionResult()
        data class Success(val user: UserDomainModel?) : SubmitUserBySessionResult()
        data class Failure(val error: Throwable) : SubmitUserBySessionResult()
    }
}
