package com.example.simplemoviecatalog.ui.recyclerViews

import com.example.simplemoviecatalog.data.model.MoviesModel
import com.example.simplemoviecatalog.domain.model.DomainModel

interface OnClickMoviesListener {

    fun onMoviesClicked(movie: DomainModel)
}