package com.example.movieapp.domain

import java.io.Serializable

data class Movie(

    val id: Int,
    val title: String,
    val voteCount: Int,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String
) : Serializable