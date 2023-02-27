package com.example.simplemoviecatalog.data.network

import com.example.simplemoviecatalog.data.model.MoviesResponse
import retrofit2.http.GET

interface APIService {
      //Movies
    @GET("3/movie/popular?api_key=b1bc4f98ee6343c991e3ee0d83868679")
    suspend fun getPopularMovies(): MoviesResponse
}