package com.example.simplemoviecatalog.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplemoviecatalog.data.database.dao.FavoritesDao
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities

@Database (entities = [FavoritesEntities::class], version = 1)
abstract class FavoritesDataBase:RoomDatabase() {
    abstract fun getFavoritesDao(): FavoritesDao
}