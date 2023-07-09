package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.domain.useCase.favorites.DeleteFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.GetFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.InsertFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.IsCheckedUseCase
import com.example.simplemoviecatalog.ui.UIState
import com.example.simplemoviecatalog.ui.model.UIModel
import com.example.simplemoviecatalog.ui.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val insertUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val isCheckedUseCase: IsCheckedUseCase
) :
    ViewModel() {

    private val _favoriteStateFlow =
        MutableStateFlow<UIState<List<UIModel>>>(UIState.Loading)

    val favoriteUIState: StateFlow<UIState<List<UIModel>>> = _favoriteStateFlow

    fun getFavorites() {
        viewModelScope.launch {
            getFavoriteUseCase().collect { favorite ->
                when (favorite) {
                    NetworkState.Loading -> TODO()
                    is NetworkState.Success -> {
                        val getFavorite = favorite.data.map { it.toUIModel() }
                        _favoriteStateFlow.value = UIState.Success(getFavorite)
                    }

                    is NetworkState.Error -> {
                        _favoriteStateFlow.value = UIState.Error(Error())
                    }
                }
            }

        }
    }

    fun addToFavorites(movie: UIModel) {
        viewModelScope.launch {
            val id = UUID.randomUUID().hashCode() // generamos un nuevo identificador único
            val favorite = DomainModel(
                id = id,
                title = movie.title,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                image = movie.image
            )
            insertUseCase.addToFavorites(favorite)
        }
    }

    fun deleteFavoriteMovie(id: String) {
        viewModelScope.launch {
            deleteFavoriteUseCase.removeToFavorites(id)
        }
    }

    suspend fun isChecked(id: String): Boolean =
        isCheckedUseCase.checkFavorite(id)
}