package com.example.simplemoviecatalog.ui.recyclerViews

import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel

interface OnClickFavoritesListener {

    fun onFavoritesClicked(favorite: FavoritesEntities)
}