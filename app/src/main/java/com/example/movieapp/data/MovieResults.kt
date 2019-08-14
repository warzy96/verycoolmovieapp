package com.example.movieapp.data

import com.squareup.moshi.Json

class MovieResults {

    @Json(name = "page")
    val page: Int? = null

    @Json(name = "total_results")
    private val totalResults: Int? = null

    @Json(name = "total_pages")
    private val totalPages: Int? = null

    @Json(name = "results")
    val results: List<ApiMovie>? = null

}