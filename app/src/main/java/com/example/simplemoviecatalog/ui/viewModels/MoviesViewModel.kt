package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.domain.MovieList
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.useCase.movies.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) :
    ViewModel() {

    private val _getMoviesLiveData = MutableLiveData<NetworkState<MovieList>>()
    val getMoviesLiveData: LiveData<NetworkState<MovieList>> = _getMoviesLiveData

    init {
        callMoviesUseCase()
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

}