package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.domain.useCase.favorites.DeleteFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.GetFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.InsertFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.VerifyFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val verifyFavoriteUseCase: VerifyFavoriteUseCase
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

    fun addToFavorites(favorite: DomainFavoritesModel) {
        viewModelScope.launch {
            favoriteUseCase.addToFavorites(favorite)
        }
    }

    fun deleteFavoriteMovie(movie: DomainFavoritesModel) {
        viewModelScope.launch {
            deleteFavoriteUseCase.removeToFavorites(movie)
            getFavorites()
        }
    }
    suspend fun isFavorite(title: String): Boolean {
        return verifyFavoriteUseCase.verifyFavorite(title)
    }

    fun verifyFavorite(title: String): Boolean {
        var isFavorite = false
        viewModelScope.launch {
            isFavorite = isFavorite(title)
            verifyLiveData.postValue(isFavorite)
        }
        return isFavorite
    }
}