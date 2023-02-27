package com.example.simplemoviecatalog.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simplemoviecatalog.data.model.FavoritesModel


@Entity(tableName = "favorites_table")
data class FavoritesEntities(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "vote_average") val voteAverage: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "image") val image: String
)

fun FavoritesModel.toFavoritesDataBase() = FavoritesEntities(
    id = id.toInt(),
    title = title,
    voteAverage = voteAverage,
    overview = overview,
    releaseDate = releaseDate,
    image = image
)
