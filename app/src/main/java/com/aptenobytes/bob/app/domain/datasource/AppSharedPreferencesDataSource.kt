package com.aptenobytes.bob.app.domain.datasource

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel

interface AppSharedPreferencesDataSource {

    // user session
    suspend fun getUserSession(): UserDomainModel?

    suspend fun setUserSession(user: UserDomainModel?): UserDomainModel?

    suspend fun clearUserSession()

    // departments
    suspend fun getDepartments(
        hotelId: Long? = null
    ): List<DepartmentDomainModel>

    suspend fun setDepartments(departments: List<DepartmentDomainModel>?)

}
