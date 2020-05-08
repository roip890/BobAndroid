package com.aptenobytes.bob.app.domain.datasource

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel

interface AppSharedPreferencesDataSource {

    // settings
    suspend fun getDepartments(
        hotelId: Long? = null
    ): List<DepartmentDomainModel>

    suspend fun setDepartments(departments: List<DepartmentDomainModel>?)

}
