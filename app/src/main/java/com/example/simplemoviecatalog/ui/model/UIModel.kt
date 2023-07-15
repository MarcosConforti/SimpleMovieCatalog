package com.example.simplemoviecatalog.ui.model

import android.os.Parcelable
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.data.database.entities.MoviesEntities
import com.example.simplemoviecatalog.domain.model.DomainModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UIModel(
    var id:Int = 0,
    val title: String,
    val voteAverage: String,
    val releaseDate: String,
    val overview:String,
    val image: String
):Parcelable
//funciones de extension para realizar los mapers
fun DomainModel.toUIModel() = UIModel(id,title, voteAverage,releaseDate,overview,image)

fun UIModel.toDomainModel() = DomainModel(id,title, voteAverage,releaseDate,overview,image)

fun FavoritesEntities.toDomainMovie() = DomainModel(id,title, voteAverage,releaseDate,overview, image)
