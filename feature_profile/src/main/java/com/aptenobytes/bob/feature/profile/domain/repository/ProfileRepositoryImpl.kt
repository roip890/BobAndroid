package com.aptenobytes.bob.feature.profile.domain.repository

import com.aptenobytes.bob.app.domain.enums.usersort.UserSortType
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileCacheDataSource
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileLocalDataSource
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileNetworkDataSource
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileSharedPreferencesDataSource

class ProfileRepositoryImpl(
    private val profileNetworkDataSource: ProfileNetworkDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val profileCacheDataSource: ProfileCacheDataSource,
    private val profileSharedPreferencesDataSource: ProfileSharedPreferencesDataSource
) : ProfileRepository {

    override suspend fun getUsers(
        userId: Long?,

        hotelId: Long?,
        email: String?,

        firstName: String?,
        lastName: String?,
        statuses: ArrayList<UserStatusType>?,
        departments: ArrayList<DepartmentDomainModel>?,

        sort: UserSortType?,
        index: Int?,
        limit: Int?
    ): List<UserDomainModel> {
        return profileNetworkDataSource.getUsers(
            userId = userId,

            hotelId = hotelId,
            email = email,
            firstName = firstName,
            lastName = lastName,

            statuses = statuses,
            departments = departments,
            sort = sort,
            index = index,
            limit = limit
        )
    }

    override suspend fun updateUser(user: UserDomainModel): UserDomainModel? {
        return profileNetworkDataSource.updateUser(user = user)
    }


}
