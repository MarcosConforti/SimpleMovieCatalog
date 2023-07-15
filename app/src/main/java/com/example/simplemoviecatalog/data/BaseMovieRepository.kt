package com.example.simplemoviecatalog.data

import com.example.simplemoviecatalog.data.database.dao.MoviesDao
import com.example.simplemoviecatalog.data.database.entities.MoviesEntities
import com.example.simplemoviecatalog.data.network.APIService
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.domain.model.toDomainMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseMovieRepository<T>(
    //dejamos declarada la variable api en caso de necesitar usarla
    private val api: APIService,
    private val dao: MoviesDao
) {
    fun getFromApi(
        apiCall: suspend () -> List<T>,
        mapToEntity: (T) -> MoviesEntities,
        mapToDomain: (T) -> DomainModel
    ): Flow<NetworkState<List<DomainModel>>> {
        return flow {
            try {
                val response = apiCall.invoke()
                if (response.isNotEmpty()) {
                    cleanList()
                    insertItems(response.map(mapToEntity))
                    emit(NetworkState.Success(response.map(mapToDomain)))
                } else {
                    val itemsDb = getFromDatabase()
                    emit(NetworkState.Success(itemsDb))
                }
            } catch (e: Throwable) {
                emit(NetworkState.Error(e))
            }
        }
    }

    suspend fun getFromDatabase(): List<DomainModel> {
        val response = dao.getAllMovies()
        return response.map { it.toDomainMovie() }
    }

    suspend fun insertItems(items: List<MoviesEntities>) = dao.insertAll(items)

    suspend fun cleanList() = dao.deleteAllMovies()
}