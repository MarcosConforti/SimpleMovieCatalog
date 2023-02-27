package com.example.simplemoviecatalog.domain.useCase.movies

import com.example.simplemoviecatalog.data.MoviesRepository
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): NetworkState<List<DomainModel>> =
        moviesRepository.getPopularMoviesFromApi()
}
