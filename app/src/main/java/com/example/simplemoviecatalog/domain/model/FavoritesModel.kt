package com.example.simplemoviecatalog.domain.model

import android.os.Parcelable
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.data.model.FavoritesModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainFavoritesModel(
    var id:Int = 0,
    val title: String,
    val voteAverage: String,
    val releaseDate: String,
    val overview:String,
    val image: String
):Parcelable
//funcion de extension para realizar los mapers
fun FavoritesEntities.toDomainFavoritesModel() =
    DomainFavoritesModel(id, title, voteAverage, releaseDate, overview, image)
fun DomainFavoritesModel.toFavoritesEntities() =
    FavoritesEntities(id, title, releaseDate, voteAverage, overview, image)
