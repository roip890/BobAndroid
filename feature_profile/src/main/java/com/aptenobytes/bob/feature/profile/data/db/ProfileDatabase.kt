package com.aptenobytes.bob.feature.profile.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [], version = 1, exportSchema = false)
@TypeConverters(
)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var instance : ProfileDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }

        private fun buildDatabase(context: Context) : ProfileDatabase {
            return Room.databaseBuilder(context.applicationContext, ProfileDatabase::class.java,
                PROFILE_DATABASE
            ).build()
        }
    }
}

private const val PROFILE_DATABASE = "profile"
