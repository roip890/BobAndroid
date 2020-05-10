package com.aptenobytes.bob.app.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.app.domain.datasource.AppSharedPreferencesDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val APP_SHARED_PREFERENCES_DEPARTMENTS_NAME = "departments"

class AppSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : AppSharedPreferencesDataSource {

    // settings
    override suspend fun getDepartments(hotelId: Long?): List<DepartmentDomainModel> {
        return withContext(Dispatchers.IO) {
            if (prefs.contains(APP_SHARED_PREFERENCES_DEPARTMENTS_NAME)) {
                moshi.adapter<Any>(Types.newParameterizedType(MutableList::class.java, DepartmentDomainModel::class.java))
                    .fromJson(prefs.getString(APP_SHARED_PREFERENCES_DEPARTMENTS_NAME, null)!!)
            }
            listOf<DepartmentDomainModel>()
        }
    }

    override suspend fun setDepartments(departments: List<DepartmentDomainModel>?) {
        if (departments != null) {
            this.prefs.edit()
                .putString(APP_SHARED_PREFERENCES_DEPARTMENTS_NAME,
                    moshi.adapter<Any>(Types.newParameterizedType(MutableList::class.java, DepartmentDomainModel::class.java)).toJson(departments))
                .apply()
        }
    }

}
