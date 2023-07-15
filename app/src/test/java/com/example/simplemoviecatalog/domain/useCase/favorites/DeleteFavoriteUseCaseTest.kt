package com.example.simplemoviecatalog.domain.useCase.favorites

import com.example.simplemoviecatalog.data.FavoritesRepository
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
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
                DomainFavoritesModel(
                    1,
                    "title",
                    "voteAverage",
                    "releaseDate",
                    "overview",
                    "image"
                )
            coEvery { repository.getFavorite() }
            //When
             deleteFavoriteUseCase.removeToFavorites(favoriteList)
            //Then
            coVerify(exactly = 1) { repository.cleanList(favoriteList) }
        }
    }

    @Test
    fun `when favorites are empty`() {
        runBlocking {
            // Given
            coEvery { repository.getFavorite() } returns emptyList()

            // When
            val response = repository.getFavorite()

            // Then
            coVerify(exactly = 1) { repository.getFavorite() }
            assert(response == emptyList<DomainFavoritesModel>())
        }
    }
}