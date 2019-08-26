package com.example.movieapp.data.api.model

import com.squareup.moshi.Json

class ApiGenreResults {

    @Json(name = "genres")
    val genres: List<ApiGenre>? = null
}