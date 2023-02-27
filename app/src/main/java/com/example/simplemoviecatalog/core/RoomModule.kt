package com.example.simplemoviecatalog.core

import android.content.Context
import androidx.room.Room
import com.example.simplemoviecatalog.data.database.FavoritesDataBase
import com.example.simplemoviecatalog.data.database.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val MOVIES_DATABASE_NAME = "MOVIES_DATABASE"
    private const val FAVORITES_DATABASE_NAME = "FAVORITES_DATABASE"


    @Singleton
    @Provides
    fun provideMoviesRoom(@ApplicationContext context: Context): MoviesDataBase {

        return Room.databaseBuilder(context, MoviesDataBase::class.java, MOVIES_DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(db: MoviesDataBase) = db.getMoviesDao()

    @Singleton
    @Provides
    fun provideFavoritesRoom(@ApplicationContext context: Context): FavoritesDataBase {

        return Room.databaseBuilder(context, FavoritesDataBase::class.java, FAVORITES_DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideFavoritesDao(db: FavoritesDataBase) = db.getFavoritesDao()
}