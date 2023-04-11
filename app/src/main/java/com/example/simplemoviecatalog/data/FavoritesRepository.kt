package com.example.simplemoviecatalog.data

import android.util.Log
import com.example.simplemoviecatalog.data.database.dao.FavoritesDao
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.domain.model.toDomainFavoritesModel
import com.example.simplemoviecatalog.domain.model.toFavoritesEntities
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoritesDao: FavoritesDao
) {

    suspend fun addToFavorites(favorites: DomainFavoritesModel) {
        favoritesDao.insertFavorites(favorites.toFavoritesEntities())
    }

    suspend fun getFavorite(): List<DomainFavoritesModel> {
        val favorite = favoritesDao.getFavorites()
        return if (favorite.isNotEmpty()) {
            favorite.map { it.toDomainFavoritesModel() }
        } else {
            Log.d("FavoritesRepository", "Lista de favoritos actualizada: $favorite") //Agregar esta línea
            emptyList()
        }
    }

    suspend fun cleanList(movie : DomainFavoritesModel) =
        favoritesDao.deleteFromFavorites(movie.toFavoritesEntities())

    suspend fun verificarSiPeliculaEsFavorita(title: String): Boolean =
         favoritesDao.verificarSiPeliculaEsFavorita(title)

}