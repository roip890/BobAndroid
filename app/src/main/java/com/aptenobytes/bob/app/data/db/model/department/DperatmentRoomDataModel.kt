package com.aptenobytes.bob.app.data.db.model.department

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.squareup.moshi.Json

@Entity(
    tableName = "department_table"
)
data class DepartmentRoomDataModel(
    @ColumnInfo(name = "department_name") @PrimaryKey @NonNull
    @field:Json(name = "name")
    val name: String
)

fun DepartmentRoomDataModel.toDomainModel() =
    DepartmentDomainModel(
        name = this.name
    )

fun DepartmentDomainModel.toRoomModel(): DepartmentRoomDataModel {
    return DepartmentRoomDataModel(
        name = this.name
    )
}
