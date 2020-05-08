package com.aptenobytes.bob.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aptenobytes.bob.app.data.db.converters.GuestRoomDataModelConverter
import com.aptenobytes.bob.app.data.db.converters.UserRoomDataModelConverter
import com.aptenobytes.bob.app.data.db.model.department.DepartmentRoomDataModel

private const val APP_DATABASE = "app"

@Database(entities = [(DepartmentRoomDataModel::class)], version = 1, exportSchema = false)
@TypeConverters(
    GuestRoomDataModelConverter::class,
    UserRoomDataModelConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var instance : AppDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                )
                    .also { instance = it }
        }

        private fun buildDatabase(context: Context) : AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                APP_DATABASE
            ).build()
        }
    }
}
