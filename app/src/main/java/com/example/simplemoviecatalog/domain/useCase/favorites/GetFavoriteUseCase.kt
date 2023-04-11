package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

    suspend operator fun invoke(): List<DomainFavoritesModel> = favoritesRepository.getFavorite()
}