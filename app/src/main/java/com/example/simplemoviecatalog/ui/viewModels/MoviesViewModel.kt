package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.data.MoviesRepository
import com.example.simplemoviecatalog.domain.MovieList
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.domain.useCase.movies.GetPopularMoviesUseCase
import com.example.simplemoviecatalog.domain.useCase.movies.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    //private val moviesRepository: MoviesRepository
) :
    ViewModel() {

    private val _getMoviesLiveData = MutableLiveData<NetworkState<MovieList>>()
    val getMoviesLiveData: LiveData<NetworkState<MovieList>> = _getMoviesLiveData

    /*private val _searchMovie = MutableLiveData<String>()
    val searchMovie: LiveData<String> = _searchMovie*/

    init {
        callMoviesUseCase()
        //searchQuery("query")
    }

    private fun callMoviesUseCase() {
        viewModelScope.launch {

            val popularResult = getPopularMoviesUseCase()

            if (popularResult is NetworkState.Success) {

                _getMoviesLiveData.value = NetworkState.Success(
                    MovieList(
                        popular = popularResult.data
                    )
                )
            } else {
                _getMoviesLiveData.value = NetworkState.Error(Error())
            }

        }
    }

    /*fun searchMovie(movie: String) {
        viewModelScope.launch {
            val searchItem = searchMovieUseCase
            _searchMovie.value = movie
        }

    }*/
    /*fun searchQuery(query: String?) {
        viewModelScope.launch {
            moviesRepository.searchMovie(query)
            _searchMovie.value = query.toString()
        }
    }*/
}