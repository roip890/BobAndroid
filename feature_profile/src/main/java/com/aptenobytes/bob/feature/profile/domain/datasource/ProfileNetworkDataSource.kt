package com.aptenobytes.bob.feature.profile.domain.datasource

import com.aptenobytes.bob.app.domain.enums.usersort.UserSortType
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel


interface ProfileNetworkDataSource {

    // users
    suspend fun getUsers(
        userId: Long? = null,
        hotelId: Long? = null,
        email: String? = null,

        firstName: String? = null,
        lastName: String? = null,
        statuses: ArrayList<UserStatusType>? = arrayListOf(),
        departments: ArrayList<DepartmentDomainModel>? = arrayListOf(),

        sort: UserSortType? = null,
        index: Int? = 0,
        limit: Int? = 20
    ): List<UserDomainModel>

    // update user
    suspend fun updateUser(user: UserDomainModel): UserDomainModel?

}
