package com.aptenobytes.bob.feature.profile.data.network.datasource

import com.aptenobytes.bob.app.data.network.enums.toNetworkEnum
import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.aptenobytes.bob.app.data.network.model.user.toDomainModel
import com.aptenobytes.bob.app.data.network.model.user.toNetworkModel
import com.aptenobytes.bob.app.domain.enums.usersort.UserSortType
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.data.network.retrofit.request.UpdateUserRequest
import com.aptenobytes.bob.feature.profile.data.network.retrofit.request.UpdateUserRequestWrapper
import com.aptenobytes.bob.feature.profile.data.network.retrofit.service.ProfileRetrofitService
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileNetworkDataSource
import com.aptenobytes.bob.library.base.extensions.collections.toArrayList
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

internal class ProfileNetworkDataSourceImpl(
    private val profileRetrofitService: ProfileRetrofitService
) : ProfileNetworkDataSource {

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
    ): List<UserDomainModel>
    =         profileRetrofitService.getUsersAsync(
        userId = userId,

        hotelId = hotelId,
        email = email,
        firstName = firstName,
        lastName = lastName,
        statuses = statuses?.map {
            it.toNetworkEnum().value
        }?.toArrayList(),
        departments = departments?.map {
            it.name
        }?.toArrayList(),
        sort = sort?.toNetworkEnum()?.value,
        index = index,
        limit = limit
    )
        ?.response
        ?.users
        ?.map { it.toDomainModel() }
        ?: listOf()


    override suspend fun updateUser(user: UserDomainModel): UserDomainModel?
        = profileRetrofitService.updateUserAsync(
            updateUserRequestWrapper = UpdateUserRequestWrapper(
                request = UpdateUserRequest(
                    user = user.toNetworkModel()
                )
            )
        )
            ?.response
            ?.user
            ?.toDomainModel()

    override suspend fun setUserStatus(userId: Long, status: UserStatusType): UserDomainModel?
        = profileRetrofitService.updateUserAsync(
        updateUserRequestWrapper = UpdateUserRequestWrapper(
            request = UpdateUserRequest(
                user = UserNetworkDataModel(
                    id = userId,
                    status = status.toNetworkEnum()
                )
            )
        )
    )
        ?.response
        ?.user
        ?.toDomainModel()

    override suspend fun uploadProfilePicture(userId: Long, imagePath: String): String? {
        val profilePicture = File(imagePath)
        val requestFile = profilePicture.asRequestBody("image".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("img", profilePicture.name, requestFile)

        return profileRetrofitService.uploadProfilePictureAsync(
            userId = userId.toString(),
            imageName = profilePicture.name,
            imageFile = body
        )
            ?.response
            ?.url
    }

}
