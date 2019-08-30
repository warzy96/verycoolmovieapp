package com.example.movieapp.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(DbMovieDetails::class, DbGenre::class, DbProductionCountry::class, MovieGenreJoin::class, MovieCountryJoin::class),
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDetailsDao(): MovieDetailsDao

    abstract fun genreDao(): GenreDao

    abstract fun productionCountryDao(): ProductionCountryDao

    abstract fun movieGenreJoinDao(): MovieGenreJoinDao

    abstract fun movieCountryJoinDao(): MovieCountryJoinDao
}