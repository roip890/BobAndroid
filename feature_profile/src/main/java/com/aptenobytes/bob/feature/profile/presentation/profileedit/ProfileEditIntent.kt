package com.aptenobytes.bob.feature.profile.presentation.profileedit

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class ProfileEditIntent : BaseIntent {
    object GetUserBySessionIntent : ProfileEditIntent()
    class UpdateUserBySessionIntent(val user: UserDomainModel) : ProfileEditIntent()
    class SubmitUserBySessionIntent(val user: UserDomainModel) : ProfileEditIntent()
}
