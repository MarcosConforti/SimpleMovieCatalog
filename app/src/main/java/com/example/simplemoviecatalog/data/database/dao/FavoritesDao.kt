package com.example.simplemoviecatalog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities

@Dao
interface FavoritesDao {

    @Insert
    suspend fun insert(favorite: FavoritesEntities)

    @Query("SELECT id FROM favorites_table WHERE id = :id")
    suspend fun checkFavorites(id: String):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorites:List<FavoritesEntities>)

    //insertar una pelicula

    @Query("DELETE FROM favorites_table WHERE id = :id")
    suspend fun deleteFromFavorites(id: String):Int
}