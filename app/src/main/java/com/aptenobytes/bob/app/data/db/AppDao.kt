package com.aptenobytes.bob.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aptenobytes.bob.app.data.db.model.department.DepartmentRoomDataModel
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel

@Dao
interface AppDao {
    @Query("SELECT * FROM department_table ORDER BY department_name ASC")
    suspend fun getDepartments(): List<@JvmSuppressWildcards DepartmentRoomDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setDepartments(departments: List<DepartmentRoomDataModel>)

    @Query("DELETE FROM department_table")
    suspend fun clearDepartments()
}
