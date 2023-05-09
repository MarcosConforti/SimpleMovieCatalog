package com.example.simplemoviecatalog.ui.adapters.movie

import com.example.simplemoviecatalog.domain.model.DomainModel

interface OnClickMoviesListener {

    fun onMoviesClicked(movie: DomainModel)
}