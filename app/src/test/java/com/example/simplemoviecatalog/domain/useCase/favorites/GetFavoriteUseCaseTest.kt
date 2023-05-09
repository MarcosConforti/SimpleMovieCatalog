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
                DomainFavoritesModel(
                    1,
                    "title",
                    "voteAverage",
                    "releaseDate",
                    "overview",
                    "image"
                )
            )
            coEvery { repository.getFavorite() } returns favoriteList
            //When
            val response = getFavoriteUseCase()
            //Then
            coVerify(exactly = 1) { repository.getFavorite() }
            assert(response == favoriteList)
        }
    }

    @Test
    fun `when proyect doesnt return a favorite`() {
        runBlocking {
            //Given
            coEvery { repository.getFavorite() } returns emptyList()
            //Then
            val response = getFavoriteUseCase()
            //When
            coVerify(exactly = 1) { repository.getFavorite() }
            assert(response == emptyList<DomainFavoritesModel>())

        }
    }
}