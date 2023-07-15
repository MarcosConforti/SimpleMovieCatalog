package com.example.simplemoviecatalog.data.network

import com.example.simplemoviecatalog.data.model.MoviesResponse
import retrofit2.http.GET

interface APIService {
      //Movies
    @GET("3/movie/popular")
    suspend fun getPopularMovies(): MoviesResponse
}