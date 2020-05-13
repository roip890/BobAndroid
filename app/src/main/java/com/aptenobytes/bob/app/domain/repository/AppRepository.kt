package com.aptenobytes.bob.app.domain.repository

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel


interface AppRepository {

    suspend fun getDepartments(
        hotelId: Long? = null
    ): List<DepartmentDomainModel>

    suspend fun emailLogin(
        user: UserDomainModel?
    ): UserDomainModel?

}
