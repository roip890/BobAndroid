package com.aptenobytes.bob.feature.profile.domain.repository

import com.aptenobytes.bob.app.domain.enums.usersort.UserSortType
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.data.network.retrofit.request.UpdateUserRequestWrapper
import com.aptenobytes.bob.feature.profile.data.network.retrofit.response.GetUsersResponseWrapper
import com.aptenobytes.bob.feature.profile.data.network.retrofit.response.UpdateUserResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*
import kotlin.collections.ArrayList

interface ProfileRepository {

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

    // user status
    suspend fun setUserStatus(userId: Long, status: UserStatusType): UserDomainModel?

    // upload profile picture
    suspend fun uploadProfilePicture(userId: Long, imagePath: String): String?

}
