package com.aptenobytes.bob.app.domain.repository

import com.aptenobytes.bob.app.domain.datasource.AppCacheDataSource
import com.aptenobytes.bob.app.domain.datasource.AppLocalDataSource
import com.aptenobytes.bob.app.domain.datasource.AppNetworkDataSource
import com.aptenobytes.bob.app.domain.datasource.AppSharedPreferencesDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel

class AppRepositoryImpl(
    private val appNetworkDataSource: AppNetworkDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val appSharedPreferencesDataSource: AppSharedPreferencesDataSource,
    private val appCacheDataSource: AppCacheDataSource
) : AppRepository {

    // departments
    override suspend fun getDepartments(
        hotelId: Long?
    ): List<DepartmentDomainModel> {
        return appNetworkDataSource.getDepartments(
            hotelId = hotelId
        )
    }

    // user session
    override suspend fun getUserSession(): UserDomainModel? {
        return appSharedPreferencesDataSource.getUserSession()
    }

    override suspend fun setUserSession(user: UserDomainModel?): UserDomainModel? {
        return appSharedPreferencesDataSource.setUserSession(
            user = user
        )
    }

    override suspend fun clearUserSession() {
        appSharedPreferencesDataSource.clearUserSession()
    }

}
