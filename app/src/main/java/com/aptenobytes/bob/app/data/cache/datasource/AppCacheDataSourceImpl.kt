package com.aptenobytes.bob.app.data.cache.datasource

import com.aptenobytes.bob.app.domain.datasource.AppCacheDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel

class AppCacheDataSourceImpl() : AppCacheDataSource {

    private var departments = listOf<DepartmentDomainModel>()

    // departments
    override suspend fun getDepartments(hotelId: Long?): List<DepartmentDomainModel> {
        return departments
    }

    override suspend fun setDepartments(departments: List<DepartmentDomainModel>?) {
        if (departments != null) this.departments = departments
    }

}
