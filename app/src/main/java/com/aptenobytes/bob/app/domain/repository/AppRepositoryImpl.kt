package com.aptenobytes.bob.app.domain.repository

import com.aptenobytes.bob.app.domain.datasource.AppCacheDataSource
import com.aptenobytes.bob.app.domain.datasource.AppLocalDataSource
import com.aptenobytes.bob.app.domain.datasource.AppNetworkDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel

class AppRepositoryImpl(
    private val appNetworkDataSource: AppNetworkDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val appCacheDataSource: AppCacheDataSource
) : AppRepository {

    override suspend fun getDepartments(
        hotelId: Long?
    ): List<DepartmentDomainModel> {
        return appNetworkDataSource.getDepartments(
            hotelId = hotelId
        )
    }

}
