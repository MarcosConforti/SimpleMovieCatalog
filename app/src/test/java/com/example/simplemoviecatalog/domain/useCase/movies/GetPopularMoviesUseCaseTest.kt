package com.example.simplemoviecatalog.domain.useCase.movies

import com.example.simplemoviecatalog.data.MoviesRepository
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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
            coEvery { repository.getPopularMoviesFromApi() } returns NetworkState.Success(movieList)

            // When
            val response = getPopularMoviesUseCase()

            // Then
            coVerify (exactly = 1){ repository.getMoviesFromDataBase() }
            assert(movieList == response)
        }
    }
    @Test
    fun `when the api returns an error`() {
        runBlocking {
            // Given
            val error = Throwable("Something went wrong")
            coEvery { repository.getPopularMoviesFromApi() } returns NetworkState.Error(error)

            // When
            val response = getPopularMoviesUseCase()

            // Then
            coVerify { repository.getPopularMoviesFromApi() }
            coVerify(exactly = 0) { repository.cleanList() }
            coVerify(exactly = 0) { repository.insertMovies(any()) }
            coVerify(exactly = 0) { repository.getMoviesFromDataBase() }
            assert(response == NetworkState.Error(error))
        }
    }
}