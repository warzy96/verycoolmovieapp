package com.example.movieapp.data.api.model

import com.squareup.moshi.Json

class ApiMovieResults {

    @Json(name = "results")
    val results: List<ApiMovie>? = null
}