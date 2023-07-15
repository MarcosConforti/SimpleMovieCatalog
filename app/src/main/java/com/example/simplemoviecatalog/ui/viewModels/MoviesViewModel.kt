package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.useCase.movies.GetPopularMoviesUseCase
import com.example.simplemoviecatalog.ui.UIState
import com.example.simplemoviecatalog.ui.model.UIList
import com.example.simplemoviecatalog.ui.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) :
    ViewModel() {

    private val _getMoviesStateFlow =
        MutableStateFlow<UIState<UIList>>(UIState.Loading)
    val getMoviesStateFlow: StateFlow<UIState<UIList>> = _getMoviesStateFlow

    init {
        callMoviesUseCase()
    }

    private fun callMoviesUseCase() {
        viewModelScope.launch {
            getPopularMoviesUseCase().collect { popularResult ->
                when (popularResult) {
                    NetworkState.Loading -> {}
                    is NetworkState.Success -> {
                        val movieList = popularResult.data.map { it.toUIModel() }
                        _getMoviesStateFlow.value = UIState.Success(
                            UIList(
                                popular = movieList
                            )
                        )
                    }
                    is NetworkState.Error -> {
                        _getMoviesStateFlow.value = UIState.Error(Error())
                    }
                }
            }
        }
    }
}