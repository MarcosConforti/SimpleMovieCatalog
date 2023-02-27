package com.example.simplemoviecatalog.domain.useCase.movies

import com.example.simplemoviecatalog.data.MoviesRepository
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import javax.inject.Inject

class SearchMovieUseCase
@Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend  fun searchMovie(query : String?) = moviesRepository.searchMovie(query)
}