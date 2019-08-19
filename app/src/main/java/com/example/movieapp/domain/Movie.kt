package com.example.movieapp.domain

import java.io.Serializable

data class Movie(

    val title: String,

    val voteCount: Int,

    val voteAverage: Double,

    val popularity: Double,

    val posterPath: String,

    val originalTitle: String,

    val originalLanguage: String,

    val genreIds: List<Int>,

    val isAdult: Boolean,

    val overview: String,

    val releaseDate: String

) : Serializable