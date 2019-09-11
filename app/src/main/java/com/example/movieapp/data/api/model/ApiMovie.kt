package com.example.movieapp.data.api.model

import com.squareup.moshi.Json

class ApiMovie {

    @field:Json(name = "vote_count")
    val voteCount: Int? = null

    @Json(name = "id")
    val id: Int? = null

    @Json(name = "popularity")
    val popularity: Double? = null

    @field:Json(name = "vote_average")
    val voteAverage: Double? = null

    @Json(name = "title")
    val title: String? = null

    @field:Json(name = "release_date")
    val releaseDate: String? = null

    @field:Json(name = "poster_path")
    val posterPath: String? = null
}