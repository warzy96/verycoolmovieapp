package com.example.movieapp.data

import com.squareup.moshi.Json

class ApiMovie {

    @Json(name = "vote_count")
    private val voteCount: Int? = null

    @Json(name = "id")
    private val id: Int? = null

    @Json(name = "video")
    private val isVideo: Boolean? = null

    @Json(name = "vote_average")
    private val voteAverage: Double? = null

    @Json(name = "title")
    val title: String? = null

    @Json(name = "popularity")
    private val popularity: Double? = null

    @Json(name = "poster_path")
    private val posterPath: String? = null

    @Json(name = "original_language")
    private val originalLanguage: String? = null

    @Json(name = "original_title")
    private val originalTitle: String? = null

    @Json(name = "genre_ids")
    private val genreIds: List<Int>? = null

    @Json(name = "backdrop_path")
    private val backdropPath: String? = null

    @Json(name = "adult")
    private val isAdult: Boolean? = null

    @Json(name = "overview")
    private val overview: String? = null

    @Json(name = "release_date")
    private val releaseDate: String? = null

}