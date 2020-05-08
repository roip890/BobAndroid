package com.aptenobytes.bob.feature.wish.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aptenobytes.bob.feature.wish.data.db.model.WishRoomDataModel

@Dao
interface WishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wish: WishRoomDataModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(wish: List<WishRoomDataModel>)

    @Query("DELETE FROM wish_table")
    suspend fun clearAllWishes()

    @Query("SELECT * FROM wish_table ORDER BY wish_id ASC")
    suspend fun getAllWishes(): List<@JvmSuppressWildcards WishRoomDataModel>
}
