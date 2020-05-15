package com.aptenobytes.bob.feature.profile.presentation.profilepage

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class ProfilePageResult : BaseResult {
  sealed class GetUserBySessionResult : ProfilePageResult() {
      object Loading : GetUserBySessionResult()
      data class Success(val user: UserDomainModel?) : GetUserBySessionResult()
      data class Failure(val error: Throwable) : GetUserBySessionResult()
  }
}
