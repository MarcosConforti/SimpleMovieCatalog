package com.example.simplemoviecatalog.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simplemoviecatalog.data.model.MoviesModel


@Entity(tableName = "movies_table")
data class MoviesEntities(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "vote_average") val voteAverage: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "image") val image: String
)

fun MoviesModel.toMovieDataBase() = MoviesEntities(
    id = id.toInt(),
    title = title,
    voteAverage = voteAverage,
    overview = overview,
    releaseDate = releaseDate,
    image = image
)
