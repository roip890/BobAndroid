package com.aptenobytes.bob.app.domain.datasource

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel

interface AppNetworkDataSource {

    suspend fun getDepartments(
        hotelId: Long? = null
    ): List<DepartmentDomainModel>

}
