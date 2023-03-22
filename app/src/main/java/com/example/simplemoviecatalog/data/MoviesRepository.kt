package com.example.simplemoviecatalog.data

import com.example.simplemoviecatalog.data.database.dao.MoviesDao
import com.example.simplemoviecatalog.data.database.entities.MoviesEntities
import com.example.simplemoviecatalog.data.database.entities.toMovieDataBase
import com.example.simplemoviecatalog.data.model.MoviesModel
import com.example.simplemoviecatalog.data.model.MoviesResponse
import com.example.simplemoviecatalog.data.network.APIService
import com.example.simplemoviecatalog.domain.MovieList
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.domain.model.toDomainMovie
import retrofit2.HttpException
import javax.inject.Inject

//esta clase funciona para seleccionar de donde el programa tomara las peliculas, si de la api o db
class MoviesRepository @Inject constructor(
    private val api: APIService,
    private val moviesDao: MoviesDao
) {
    suspend fun searchMovie(query: String?) = moviesDao.searchMovie(query)

    suspend fun getPopularMoviesFromApi(): NetworkState<List<DomainModel>> =
        //recupero las peliculas
        try {
            var moviesApi = api.getPopularMovies().results
            if (moviesApi.isNotEmpty()) {
                cleanList()
                insertMovies(moviesApi.map { it.toMovieDataBase() })
                NetworkState.Success(moviesApi.map { it.toDomainMovie() })

            } else {
                //si esta vacio, que recupere los datos de la db
                val moviesDb = getMoviesFromDataBase()
                NetworkState.Success(moviesDb)
            }

        } catch (e: Throwable) {
            NetworkState.Error(e)
        }

    private suspend fun getMoviesFromDataBase(): List<DomainModel> {
        val response = moviesDao.getAllMovies()
        return response.map { it.toDomainMovie() }
    }

    private suspend fun insertMovies(movies: List<MoviesEntities>) = moviesDao.insertAll(movies)

    suspend fun insertOnlyMovie(movie: MoviesEntities) {
        moviesDao.insert(movie)
    }

    private suspend fun cleanList() = moviesDao.deleteAllMovies()


}