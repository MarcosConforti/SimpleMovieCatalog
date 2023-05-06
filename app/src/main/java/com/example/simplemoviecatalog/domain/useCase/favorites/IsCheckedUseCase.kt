package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import javax.inject.Inject

class IsCheckedUseCase@Inject constructor(private val favoritesRepository: FavoritesRepository) {

    suspend  fun verifyFavorite(title:String): Boolean {
        return favoritesRepository.isChecked(title)
    }
}