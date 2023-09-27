package com.example.simplemoviecatalog.data

import com.example.simplemoviecatalog.data.database.dao.MoviesDao
import com.example.simplemoviecatalog.data.database.entities.toMovieDataBase
import com.example.simplemoviecatalog.data.model.MoviesModel
import com.example.simplemoviecatalog.data.network.APIService
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.domain.model.toDomainMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//esta clase funciona para seleccionar de donde el programa tomara las peliculas, si de la api o db
class MoviesRepository @Inject constructor(
    private val api: APIService,
    private val moviesDao: MoviesDao
): BaseMovieRepository<MoviesModel>(api, moviesDao) {

    fun getPopularMoviesFromApi(): Flow<NetworkState<List<DomainModel>>> {
        return getFromApi(
            apiCall = { api.getPopularMovies().results },
            mapToEntity = { it.toMovieDataBase() },
            mapToDomain = { it.toDomainMovie() }
        )
    }
}