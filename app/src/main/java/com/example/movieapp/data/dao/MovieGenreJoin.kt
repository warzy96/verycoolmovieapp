package com.example.movieapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_genre_join",
    primaryKeys = ["movie_id", "genre_id"],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DbMovieDetails::class,
            parentColumns = arrayOf("movie_id"),
            childColumns = arrayOf("movie_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DbGenre::class,
            parentColumns = arrayOf("genre_id"),
            childColumns = arrayOf("genre_id"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
class MovieGenreJoin(

    @ColumnInfo(name = "movie_id")
    val movieId: Int,

    @ColumnInfo(name = "genre_id")
    val genreId: Int
)