package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.domain.useCase.favorites.DeleteFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.GetFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.InsertFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.IsCheckedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val insertUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val isCheckedUseCase: IsCheckedUseCase
) :
    ViewModel() {

    private val _favoritesUseCase = MutableLiveData<List<DomainFavoritesModel>>()

    val favoriteLiveData: LiveData<List<DomainFavoritesModel>> = _favoritesUseCase

    val verifyLiveData = MutableLiveData<Boolean>()

    fun getFavorites() {
        viewModelScope.launch {
            val getFavoriteUseCase = getFavoriteUseCase()
            if (getFavoriteUseCase.isNotEmpty()) {
                _favoritesUseCase.value = getFavoriteUseCase
            }
        }
    }

    fun addToFavorites(movie: DomainFavoritesModel) {
        viewModelScope.launch {
            val id = UUID.randomUUID().hashCode() // generamos un nuevo identificador único
            val favorite = DomainFavoritesModel(
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

    fun deleteFavoriteMovie(favorite:DomainFavoritesModel) {
        viewModelScope.launch {
            deleteFavoriteUseCase.removeToFavorites(favorite)
        }
    }
    //funcion que verifica si es favorito
    private suspend fun isFavorite(title: String): Boolean {
        return isCheckedUseCase.verifyFavorite(title)
    }
    fun isChecked(title: String): Boolean {
        viewModelScope.launch {
            val isFavorite = isFavorite(title)
            verifyLiveData.postValue(isFavorite)
        }
        return verifyLiveData.value ?: false
    }
}