package com.example.movieapp.data.model

import com.squareup.moshi.Json

class ApiMovie {

    @field:Json(name = "vote_count")
    val voteCount: Int? = null

    @Json(name = "id")
    private val id: Int? = null

    @field:Json(name = "video")
    private val isVideo: Boolean? = null

    @field:Json(name = "vote_average")
    val voteAverage: Double? = null

    @Json(name = "title")
    val title: String? = null

    @Json(name = "popularity")
    val popularity: Double? = null

    @field:Json(name = "poster_path")
    val posterPath: String? = null

    @field:Json(name = "original_language")
    val originalLanguage: String? = null

    @field:Json(name = "original_title")
    val originalTitle: String? = null

    @field:Json(name = "genre_ids")
    val genreIds: List<Int>? = null

    @field:Json(name = "backdrop_path")
    private val backdropPath: String? = null

    @Json(name = "adult")
    val isAdult: Boolean? = null

    @Json(name = "overview")
    val overview: String? = null

    @field:Json(name = "release_date")
    val releaseDate: String? = null
}