package com.example.movieapp.data.model

import com.squareup.moshi.Json

class GenreResults {

    @Json(name = "genres")
    val genres: List<Genre>? = null
}