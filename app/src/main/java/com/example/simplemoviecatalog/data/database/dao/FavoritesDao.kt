package com.example.simplemoviecatalog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities

@Dao
interface FavoritesDao {



    @Query("SELECT * FROM favorites_table")
    suspend fun getFavorites(): List<FavoritesEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorites: FavoritesEntities)

    @Query("DELETE FROM favorites_table WHERE id = :id")
    suspend fun deleteFromFavorites(id: String): Int
}