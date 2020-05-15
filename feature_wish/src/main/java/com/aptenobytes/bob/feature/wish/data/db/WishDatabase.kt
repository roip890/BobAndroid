package com.aptenobytes.bob.feature.wish.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aptenobytes.bob.app.data.db.converters.department.DepartmentRoomDataModelConverter
import com.aptenobytes.bob.app.data.db.converters.department.DepartmentsListRoomDataModelConverter
import com.aptenobytes.bob.app.data.db.converters.guest.GuestRoomDataModelConverter
import com.aptenobytes.bob.app.data.db.converters.user.UserRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.wishelement.WishElementListRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.wishelement.WishElementRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.wishstatus.UserStatusListRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.wishstatus.WishStatusRoomDataTypeConverter
import com.aptenobytes.bob.feature.wish.data.db.model.WishRoomDataModel

@Database(entities = [(WishRoomDataModel::class)], version = 1, exportSchema = false)
@TypeConverters(
    DepartmentRoomDataModelConverter::class,
    DepartmentsListRoomDataModelConverter::class,
    GuestRoomDataModelConverter::class,
    UserRoomDataModelConverter::class,
    WishElementRoomDataModelConverter::class,
    WishElementListRoomDataModelConverter::class,
    WishStatusRoomDataTypeConverter::class,
    UserStatusListRoomDataModelConverter::class
)
abstract class WishDatabase : RoomDatabase() {
    abstract fun wishDao(): WishDao

    companion object {
        @Volatile
        private var instance : WishDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }

        private fun buildDatabase(context: Context) : WishDatabase {
            return Room.databaseBuilder(context.applicationContext, WishDatabase::class.java,
                WISH_DATABASE
            ).build()
        }
    }
}

private const val WISH_DATABASE = "wishes"
