package com.aptenobytes.bob.app.data.network.enums

import com.aptenobytes.bob.app.domain.enums.usersort.UserSortType
import com.squareup.moshi.Json

enum class UserSortNetworkDataType(val value: String) {

    @field:Json(name = "none")
    NONE(value = "none"),
    @field:Json(name = "id")
    ID(value = "id"),
    @field:Json(name = "email")
    EMAIL(value = "email"),
    @field:Json(name = "firstname")
    FIRST_NAME(value = "firstname"),
    @field:Json(name = "lastname")
    LAST_NAME(value = "lastname"),
    @field:Json(name = "")
    UNKNOWN(value = "")
}

fun UserSortNetworkDataType.toDomainEnum() = UserSortType.valueOf(this.name)

fun UserSortType.toNetworkEnum() = UserSortNetworkDataType.valueOf(this.name)
