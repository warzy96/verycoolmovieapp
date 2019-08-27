package com.example.movieapp.data.view.model

import com.example.movieapp.domain.Genre
import com.example.movieapp.domain.ProductionCountry
import java.io.Serializable

class MovieDetailsViewModel(
    val id: Int,
    val title: String,
    val tagline: String?,
    val voteCount: Int,
    val voteAverage: Double,
    val backdropPath: String,
    val originalTitle: String,
    val genres: List<Genre>,
    val countries: List<ProductionCountry>,
    val isAdult: Boolean,
    val overview: String,
    val releaseDate: String,
    val runtime: Int?,
    val homepage: String?
) : Serializable