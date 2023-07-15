package com.example.simplemoviecatalog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemoviecatalog.data.database.entities.MoviesEntities


@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_table")
    suspend fun getAllMovies():List<MoviesEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies:List<MoviesEntities>)

    @Query("DELETE FROM movies_table")
    suspend fun deleteAllMovies()

}