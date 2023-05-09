package com.example.simplemoviecatalog.data.database.dao

import androidx.room.*
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.data.model.FavoritesModel
import com.example.simplemoviecatalog.data.model.MoviesModel
import com.example.simplemoviecatalog.domain.MovieList

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites_table")
    suspend fun getFavorites(): List<FavoritesEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorites: FavoritesEntities)

    @Delete
    suspend fun deleteFromFavorites(movie:FavoritesEntities)

    @Query("SELECT EXISTS(SELECT * FROM favorites_table WHERE title = :title)")
    suspend fun isChecked(title:String): Boolean
}