package com.example.movieapp.data.api.model

import com.squareup.moshi.Json

class ApiMovieDetails {

    @field:Json(name = "vote_count")
    val voteCount: Int? = null

    @Json(name = "id")
    val id: Int? = null

    @field:Json(name = "vote_average")
    val voteAverage: Double? = null

    @Json(name = "title")
    val title: String? = null

    @Json(name = "tagline")
    val tagline: String? = null

    @Json(name = "popularity")
    val popularity: Double? = null

    @field:Json(name = "poster_path")
    val posterPath: String? = null

    @field:Json(name = "original_language")
    val originalLanguage: String? = null

    @field:Json(name = "original_title")
    val originalTitle: String? = null

    @field:Json(name = "genres")
    val genres: List<ApiGenre>? = null

    @field:Json(name = "backdrop_path")
    val backdropPath: String? = null

    @Json(name = "adult")
    val isAdult: Boolean? = null

    @Json(name = "overview")
    val overview: String? = null

    @field:Json(name = "release_date")
    val releaseDate: String? = null

    @field:Json(name = "runtime")
    val runtime: Int? = null

    @field:Json(name = "homepage")
    val homepage: String? = null
}