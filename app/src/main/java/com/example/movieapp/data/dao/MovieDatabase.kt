package com.example.movieapp.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(
        DbMovieDetails::class,
        DbGenre::class,
        DbProductionCountry::class,
        MovieGenreJoin::class,
        MovieCountryJoin::class,
        DbMovie::class
    ),
    version = MovieDatabase.VERSION
)
abstract class MovieDatabase : RoomDatabase() {

    companion object {

        const val VERSION = 1

        fun getDB(context: Context, databaseName: String) = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            databaseName
        ).build()
    }

    abstract fun movieDao(): MovieDao

    abstract fun movieDetailsDao(): MovieDetailsDao

    abstract fun genreDao(): GenreDao

    abstract fun productionCountryDao(): ProductionCountryDao

    abstract fun movieGenreJoinDao(): MovieGenreJoinDao

    abstract fun movieCountryJoinDao(): MovieCountryJoinDao
}