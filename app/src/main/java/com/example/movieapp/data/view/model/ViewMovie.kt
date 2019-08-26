package com.example.movieapp.data.view.model

import java.io.Serializable

class ViewMovie(

    val id: Int,
    val title: String,
    val tagline: String?,
    val voteCount: Int,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val isAdult: Boolean,
    val overview: String,
    val releaseDate: String,
    val runtime: Int?,
    val homepage: String?
) : Serializable