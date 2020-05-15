package com.aptenobytes.bob.app.data.network.datasource

import com.aptenobytes.bob.app.data.network.retrofit.service.AppRetrofitService
import com.aptenobytes.bob.app.domain.datasource.AppNetworkDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel

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

}

fun String.toDepartmentDomainModel(): DepartmentDomainModel = DepartmentDomainModel(this)

fun DepartmentDomainModel.toDepartmentNetworkModel(): String = this.name
