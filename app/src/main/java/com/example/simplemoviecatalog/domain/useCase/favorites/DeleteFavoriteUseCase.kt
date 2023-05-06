package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

    suspend fun removeToFavorites(favorite:DomainFavoritesModel) =
        favoritesRepository.cleanList(favorite)
}