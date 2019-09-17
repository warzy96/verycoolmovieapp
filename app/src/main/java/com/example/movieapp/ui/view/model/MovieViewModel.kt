package com.example.movieapp.ui.view.model

import java.io.Serializable

class MovieViewModel(
    val id: Int,
    val title: String,
    val voteCount: Int,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String,
    var favorite: Boolean
) : Serializable