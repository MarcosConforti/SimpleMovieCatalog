package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

     operator fun invoke(): Flow<NetworkState<List<DomainModel>>> =
         favoritesRepository.getFavorite()
}