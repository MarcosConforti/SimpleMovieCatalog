package com.example.simplemoviecatalog.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results") val results: List<MoviesModel>
)
