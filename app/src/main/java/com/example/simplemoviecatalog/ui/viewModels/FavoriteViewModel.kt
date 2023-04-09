package com.example.simplemoviecatalog.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.domain.useCase.favorites.GetFavoriteUseCase
import com.example.simplemoviecatalog.domain.useCase.favorites.InsertFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteUseCase: InsertFavoriteUseCase,
private val getFavoriteUseCase: GetFavoriteUseCase) :
    ViewModel() {

    private val _favoritesUseCase = MutableLiveData<List<FavoritesEntities>>()

    val favoriteLiveData: LiveData<List<FavoritesEntities>> = _favoritesUseCase


    fun addToFavorites(favorite: FavoritesEntities){
        viewModelScope.launch {
            favoriteUseCase.addToFavorites(favorite)
        }
    }
    fun getFavorites(){
        viewModelScope.launch {
            val getFavoriteUseCase = getFavoriteUseCase()
            if(getFavoriteUseCase.isNotEmpty()){
                _favoritesUseCase.value = getFavoriteUseCase
            }

        }
    }
    fun deleteFavoriteMovie(movie: FavoritesEntities){
        viewModelScope.launch {
            favoriteUseCase.removeToFavorites(movie)
            getFavorites()
        }
    }

}