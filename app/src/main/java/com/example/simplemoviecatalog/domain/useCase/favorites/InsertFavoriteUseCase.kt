package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

    suspend fun addToFavorites(favorites: FavoritesEntities) =
        favoritesRepository.addToFavorites(favorites)

    //suspend operator fun invoke() = favoritesRepository.getFavorite()

    suspend fun removeToFavorites(title:String) = favoritesRepository.cleanList(title)

}