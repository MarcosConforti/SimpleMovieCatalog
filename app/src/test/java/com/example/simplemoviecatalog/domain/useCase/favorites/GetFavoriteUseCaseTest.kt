package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFavoriteUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: FavoritesRepository

    lateinit var getFavoriteUseCase: GetFavoriteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFavoriteUseCase = GetFavoriteUseCase(repository)
    }

    @Test
    fun `when the proyect return something in favorite`() {
        runBlocking {
            //Given
            val favoriteList = listOf(
                DomainModel(
                    1,
                    "title",
                    "voteAverage",
                    "releaseDate",
                    "overview",
                    "image"
                )
            )
            val success: Flow<NetworkState<List<DomainModel>>> = flowOf(NetworkState.Success(favoriteList))
            coEvery { repository.getFavorite() } returns success
            //When
            val response = getFavoriteUseCase()
            //Then
            coVerify(exactly = 1) { repository.getFavorite() }
            assert(response == success)
        }
    }

    @Test
    fun `when proyect doesnt return a favorite`() {
        runBlocking {
            //Given
            val empty: Flow<NetworkState<List<DomainModel>>> = flowOf()
            coEvery { repository.getFavorite() } returns empty
            //Then
            val response = getFavoriteUseCase()
            //When
            coVerify(exactly = 1) { repository.getFavorite() }
            assert(response == empty)

        }
    }
}