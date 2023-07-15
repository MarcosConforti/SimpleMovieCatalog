package com.example.simplemoviecatalog.data

import com.example.simplemoviecatalog.data.database.dao.FavoritesDao
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.domain.model.toDomainModel
import com.example.simplemoviecatalog.domain.model.toFavoritesEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoritesDao: FavoritesDao
) {

     fun getFavorite(): Flow<NetworkState<List<DomainModel>>> =
        favoritesDao.getFavorites().map { favorites ->
            if (favorites.isNotEmpty()){
                NetworkState.Success(favorites.map { it.toDomainModel() })
            }
            else{
                NetworkState.Success(emptyList())
            }
        }

    suspend fun addToFavorites(favorites: DomainModel) {
        favoritesDao.insertFavorites(favorites.toFavoritesEntities())
    }

    suspend fun cleanList(id: String) =
        favoritesDao.deleteFromFavorites(id)

    suspend fun isChecked(id: String): Boolean =
        favoritesDao.checkFavorites(id)
}