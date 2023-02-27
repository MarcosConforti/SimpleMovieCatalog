package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.data.model.FavoritesModel
import com.example.simplemoviecatalog.domain.useCase.favorites.InsertFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteUseCase: InsertFavoriteUseCase) :
    ViewModel() {

    private val _favoritesUseCase = MutableLiveData<List<FavoritesModel>>()

    val favoriteLiveData: LiveData<List<FavoritesModel>> = _favoritesUseCase


    fun addToFavorites(favorite: FavoritesEntities){
        viewModelScope.launch {
            favoriteUseCase.addToFavorites(
                FavoritesEntities(
                    favorite.id,
                    favorite.title,
                    favorite.releaseDate,
                    favorite.voteAverage,
                    favorite.overview,
                    favorite.image
                )
            )
        }
    }

    suspend fun checkFavorite(id: String) = favoriteUseCase.checkFavorites(id)

    suspend fun removeFavorites(id: String) {
        viewModelScope.launch {
            favoriteUseCase.removeToFavorites(id)
        }
    }
}