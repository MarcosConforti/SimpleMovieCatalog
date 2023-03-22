package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

    suspend operator fun invoke(): List<FavoritesEntities> = favoritesRepository.getFavorite()
}