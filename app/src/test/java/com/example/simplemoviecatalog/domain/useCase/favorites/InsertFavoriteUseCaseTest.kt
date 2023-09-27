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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InsertFavoriteUseCaseTest{
    @RelaxedMockK
    private lateinit var repository: FavoritesRepository

    lateinit var insertFavoriteUseCase: InsertFavoriteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        insertFavoriteUseCase = InsertFavoriteUseCase(repository)
    }

    @Test
    fun `when insert a favorite in DataBase`() {
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
            insertFavoriteUseCase.addToFavorites(favoriteList)
            //Then
            coVerify(exactly = 1) { repository.addToFavorites(favoriteList) }
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