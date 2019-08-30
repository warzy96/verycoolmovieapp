package com.example.movieapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details_table")
data class DbMovieDetails(

    @PrimaryKey @ColumnInfo(name = "movie_id")
    val id: Int,

    val title: String,

    val tagline: String?,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    val popularity: Double,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "poster_language")
    val originalLanguage: String,

    @ColumnInfo(name = "is_adult")
    val isAdult: Boolean,

    val overview: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    val runtime: Int?,

    val homepage: String?
)