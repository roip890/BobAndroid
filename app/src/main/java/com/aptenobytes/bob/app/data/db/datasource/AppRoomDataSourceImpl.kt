package com.aptenobytes.bob.app.data.db.datasource

import com.aptenobytes.bob.app.data.db.AppDao
import com.aptenobytes.bob.app.data.db.model.department.toDomainModel
import com.aptenobytes.bob.app.data.db.model.department.toRoomModel
import com.aptenobytes.bob.app.domain.datasource.AppLocalDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel

class AppRoomDataSourceImpl(private val dao: AppDao) : AppLocalDataSource {

    override suspend fun getDepartments(hotelId: Long?): List<DepartmentDomainModel> {
        return dao.getDepartments()
            .map { it.toDomainModel() }
    }

    override suspend fun setDepartments(departments: List<DepartmentDomainModel>?) {
        departments?.let {
            dao.setDepartments(departments.map { it.toRoomModel() })
        }
    }


}
