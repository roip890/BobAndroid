package com.aptenobytes.bob.feature.wish.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aptenobytes.bob.feature.wish.data.db.converters.GuestRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.UserRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.WishDepartmentsListRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.WishElementListRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.WishElementRoomDataModelConverter
import com.aptenobytes.bob.feature.wish.data.db.converters.WishStatusRoomDataTypeConverter
import com.aptenobytes.bob.feature.wish.data.db.model.WishRoomDataModel

private const val WISH_DATABASE = "wishes"

@Database(entities = [(WishRoomDataModel::class)], version = 1, exportSchema = false)
@TypeConverters(
    WishDepartmentsListRoomDataModelConverter::class,
    WishElementListRoomDataModelConverter::class,
    WishElementRoomDataModelConverter::class,
    WishStatusRoomDataTypeConverter::class,
    GuestRoomDataModelConverter::class,
    UserRoomDataModelConverter::class
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
