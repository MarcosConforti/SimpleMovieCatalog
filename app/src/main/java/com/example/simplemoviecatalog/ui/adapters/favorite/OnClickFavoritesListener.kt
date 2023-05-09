package com.example.simplemoviecatalog.ui.adapters.favorite

import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities

interface OnClickFavoritesListener {

    fun onFavoritesClicked(favorite: FavoritesEntities)
}