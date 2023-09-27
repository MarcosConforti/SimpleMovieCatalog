package com.example.simplemoviecatalog.domain.useCase.movies

import com.example.simplemoviecatalog.data.MoviesRepository
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    @MockK
    private lateinit var repository: MoviesRepository

    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `when the api returns something`() {
        runBlocking {
            // Given
            val movieList = listOf(
                DomainModel(
                    1,
                    "title",
                    "voteAverage",
                    "releaseDate",
                    "overview",
                    "image"
                )
            )
            val successFlow: Flow<NetworkState<List<DomainModel>>> = flowOf(NetworkState.Success(movieList))
            coEvery { repository.getPopularMoviesFromApi() } returns successFlow


            // When
            val response = getPopularMoviesUseCase()

            // Then
            coVerify (exactly = 1){ repository.getPopularMoviesFromApi() }
            assert(response == successFlow)
        }
    }
    @Test
    fun `when the api returns an error`() {
        runBlocking {
            // Given
            val error: Flow<NetworkState<List<DomainModel>>> = flowOf( NetworkState.Error(Error()))
            coEvery { repository.getPopularMoviesFromApi() } returns error

            // When
            val response = getPopularMoviesUseCase()

            // Then
            coVerify { repository.getPopularMoviesFromApi() }
            coVerify(exactly = 0) { repository.cleanList() }
            coVerify(exactly = 0) { repository.insertItems(any()) }
            coVerify(exactly = 0) { repository.getFromDatabase() }
            assert(error == response)
        }
    }
}