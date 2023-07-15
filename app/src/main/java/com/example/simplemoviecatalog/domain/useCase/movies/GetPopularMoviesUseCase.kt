package com.example.simplemoviecatalog.domain.useCase.movies

import com.example.simplemoviecatalog.data.MoviesRepository
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

   operator fun invoke(): Flow<NetworkState<List<DomainModel>>> =
        moviesRepository.getPopularMoviesFromApi()
}
