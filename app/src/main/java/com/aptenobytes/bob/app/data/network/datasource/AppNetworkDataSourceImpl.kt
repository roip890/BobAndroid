package com.aptenobytes.bob.app.data.network.datasource

import com.aptenobytes.bob.app.data.network.model.user.toDomainModel
import com.aptenobytes.bob.app.data.network.model.user.toNetworkModel
import com.aptenobytes.bob.app.data.network.retrofit.request.EmailLoginRequest
import com.aptenobytes.bob.app.data.network.retrofit.request.EmailLoginRequestWrapper
import com.aptenobytes.bob.app.data.network.retrofit.service.AppRetrofitService
import com.aptenobytes.bob.app.domain.datasource.AppNetworkDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel

internal class AppNetworkDataSourceImpl(
    private val appRetrofitService: AppRetrofitService
) : AppNetworkDataSource {

    override suspend fun getDepartments(
        hotelId: Long?
    ) =
        appRetrofitService.getDepartmentsAsync(
            hotelId = hotelId
        )
            ?.response
            ?.departments
            ?.map {
                it.toDepartmentDomainModel()
            }
            ?: listOf()

    override suspend fun emailLogin(
        user: UserDomainModel?
    ) = appRetrofitService.emailLoginAsync(
        emailLoginRequestWrapper = EmailLoginRequestWrapper(
            request = EmailLoginRequest(
                user = user?.toNetworkModel()
            )
        )
    )
        ?.response
        ?.user
        ?.toDomainModel()

}

fun String.toDepartmentDomainModel(): DepartmentDomainModel = DepartmentDomainModel(this)

fun DepartmentDomainModel.toDepartmentNetworkModel(): String = this.name
