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

    //Ninguna funcion suspendida sera Privada, ya que tendremos que testearlas

    /*fun getPopularMoviesFromApi(): Flow<NetworkState<List<DomainModel>>> {
        //recupero las peliculas
       return flow {
            //recupero las peliculas
            try {
                var moviesApi = api.getPopularMovies().results
                if (moviesApi.isNotEmpty()) {
                    cleanList()
                    insertMovies(moviesApi.map { it.toMovieDataBase() })
                    emit(NetworkState.Success(moviesApi.map { it.toDomainMovie() }))
                } else {
                    //si esta vacio, que recupere los datos de la db
                    val moviesDb = getMoviesFromDataBase()
                    emit(NetworkState.Success(moviesDb))
                }
            } catch (e: Throwable) {
                emit(NetworkState.Error(e))
            }
        }
    }

     suspend fun getMoviesFromDataBase(): List<DomainModel> {
        val response = moviesDao.getAllMovies()
        return response.map { it.toDomainMovie() }
    }

     suspend fun insertMovies(movies: List<MoviesEntities>) = moviesDao.insertAll(movies)

     suspend fun cleanList() = moviesDao.deleteAllMovies()
*/
}