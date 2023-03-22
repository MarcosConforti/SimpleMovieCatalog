package com.example.simplemoviecatalog.data

import com.example.simplemoviecatalog.data.database.dao.FavoritesDao
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import javax.inject.Inject

//esta clase funciona para seleccionar de donde el programa tomara las peliculas, si de la api o db
class FavoritesRepository @Inject constructor(
    private val favoritesDao: FavoritesDao
) {

    suspend fun addToFavorites(favorites: FavoritesEntities) =
        favoritesDao.insertFavorites(favorites)

    suspend fun getFavorite() = favoritesDao.getFavorites()

    suspend fun cleanList(id: String) = favoritesDao.deleteFromFavorites(id)

}