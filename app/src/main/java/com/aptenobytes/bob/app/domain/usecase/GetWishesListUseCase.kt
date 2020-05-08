package com.aptenobytes.bob.app.domain.usecase

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository

class GetDepartmentsListUseCase(
    private val appRepository: AppRepository
) {
    suspend fun execute(): List<DepartmentDomainModel> {
        return appRepository.getDepartments()
    }
}
