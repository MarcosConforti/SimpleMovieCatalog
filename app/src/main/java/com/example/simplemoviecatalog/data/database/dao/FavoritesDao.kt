package com.example.simplemoviecatalog.data.database.dao

import androidx.room.*
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.data.model.FavoritesModel
import com.example.simplemoviecatalog.data.model.MoviesModel
import com.example.simplemoviecatalog.domain.MovieList
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites_table")
    fun getFavorites(): Flow<List<FavoritesEntities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorites: FavoritesEntities)

    @Query("DELETE FROM favorites_table WHERE id = :id")
    suspend fun deleteFromFavorites(id: String): Int

    @Query("SELECT EXISTS(SELECT * FROM favorites_table WHERE id = :id)")
    suspend fun checkFavorites(id: String): Boolean
}