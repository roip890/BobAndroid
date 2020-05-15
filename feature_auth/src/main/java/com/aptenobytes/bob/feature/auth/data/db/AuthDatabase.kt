package com.aptenobytes.bob.feature.auth.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [], version = 1, exportSchema = false)
@TypeConverters(
)
abstract class AuthDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao

    companion object {
        @Volatile
        private var instance : AuthDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }

        private fun buildDatabase(context: Context) : AuthDatabase {
            return Room.databaseBuilder(context.applicationContext, AuthDatabase::class.java,
                AUTH_DATABASE
            ).build()
        }
    }
}

private const val AUTH_DATABASE = "auth"
