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

class DeleteFavoriteUseCaseTest{
    @RelaxedMockK
    private lateinit var repository: FavoritesRepository

    lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        deleteFavoriteUseCase = DeleteFavoriteUseCase(repository)
    }

    @Test
    fun `when the proyect remove a favorite`() {
        runBlocking {
            //Given
            val favoriteList =
                DomainModel(
                    1,
                    "title",
                    "voteAverage",
                    "releaseDate",
                    "overview",
                    "image"
                )
            coEvery { repository.getFavorite() }
            //When
             deleteFavoriteUseCase.removeToFavorites(favoriteList.toString())
            //Then
            coVerify(exactly = 1) { repository.cleanList(favoriteList.toString()) }
        }
    }

    @Test
    fun `when favorites are empty`() {
        runBlocking {
            // Given
            val empty: Flow<NetworkState<List<DomainModel>>> = flowOf()
            coEvery { repository.getFavorite() } returns empty

            // When
            val response = repository.getFavorite()

            // Then
            coVerify(exactly = 1) { repository.getFavorite() }
            assert(response == empty)
        }
    }
}