package com.example.movieapp.data.model

import com.squareup.moshi.Json

class Genre {

    @Json(name = "id")
    val id: Int? = null

    @field:Json(name = "name")
    val name: String? = null
}