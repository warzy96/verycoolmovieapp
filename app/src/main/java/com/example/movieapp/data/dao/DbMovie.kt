package com.example.movieapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class DbMovie(

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String
)