package com.aptenobytes.bob.app.domain.repository

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel


interface AppRepository {

    // departments
    suspend fun getDepartments(
        hotelId: Long? = null
    ): List<DepartmentDomainModel>

    // user session
    suspend fun getUserSession(): UserDomainModel?

    suspend fun setUserSession(user: UserDomainModel?): UserDomainModel?

    suspend fun clearUserSession()

}
