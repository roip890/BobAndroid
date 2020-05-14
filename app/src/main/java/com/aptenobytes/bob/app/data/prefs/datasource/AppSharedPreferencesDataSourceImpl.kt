package com.aptenobytes.bob.app.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.app.domain.datasource.AppSharedPreferencesDataSource
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val APP_SHARED_PREFERENCES_DEPARTMENTS_NAME = "departments"
private const val APP_SHARED_PREFERENCES_USER_SESSION_NAME = "user_session"

class AppSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : AppSharedPreferencesDataSource {

    // user session
    override suspend fun getUserSession(): UserDomainModel? {
        return withContext(Dispatchers.IO) {
            if (prefs.contains(APP_SHARED_PREFERENCES_USER_SESSION_NAME)) {
                return@withContext moshi.adapter(UserDomainModel::class.java)
                    .fromJson(prefs.getString(APP_SHARED_PREFERENCES_USER_SESSION_NAME, null)!!)
            }
            return@withContext null
        }
    }

    override suspend fun setUserSession(user: UserDomainModel?): UserDomainModel? {
        this.clearUserSession()
        return withContext(Dispatchers.IO) {
            if (user != null) {
                prefs.edit()
                    .putString(
                        APP_SHARED_PREFERENCES_USER_SESSION_NAME, moshi.adapter(UserDomainModel::class.java)
                            .toJson(user)
                    ).apply()
                if (prefs.contains(APP_SHARED_PREFERENCES_USER_SESSION_NAME)) {
                    return@withContext moshi.adapter(UserDomainModel::class.java)
                        .fromJson(prefs.getString(APP_SHARED_PREFERENCES_USER_SESSION_NAME, null)!!)
                }
            }
            return@withContext null
        }
    }

    override suspend fun clearUserSession() {
        if (prefs.contains(APP_SHARED_PREFERENCES_USER_SESSION_NAME)) {
            prefs.edit()
                .remove(APP_SHARED_PREFERENCES_USER_SESSION_NAME)
                .apply()
        }
    }

    // departments
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
