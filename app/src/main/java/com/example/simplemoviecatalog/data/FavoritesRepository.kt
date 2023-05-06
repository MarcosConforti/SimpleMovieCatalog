package com.example.simplemoviecatalog.data

import com.example.simplemoviecatalog.data.database.dao.FavoritesDao
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
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
            emptyList()
        }
    }

    suspend fun cleanList(favorites: DomainFavoritesModel) =
        favoritesDao.deleteFromFavorites(favorites.toFavoritesEntities())

    suspend fun isChecked(title: String): Boolean =
         favoritesDao.isChecked(title)
}