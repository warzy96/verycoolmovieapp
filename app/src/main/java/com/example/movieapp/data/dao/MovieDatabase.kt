package com.example.movieapp.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.domain.DbProductionCountry

@Database(entities = arrayOf(DbMovieDetails::class, DbGenre::class, DbProductionCountry::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun genreDao(): GenreDao
    abstract fun productionCountryDao(): ProductionCountryDao
}