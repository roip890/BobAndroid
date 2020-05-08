package com.aptenobytes.bob.app.domain.repository

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel


interface AppRepository {

    suspend fun getDepartments(
        hotelId: Long? = null
    ): List<DepartmentDomainModel>

}
