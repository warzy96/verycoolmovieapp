package com.example.movieapp.data.api.model

import com.squareup.moshi.Json

class ApiProductionCountry {

    @field:Json(name = "iso_3166_1")
    val isoCode: String? = null

    @field:Json(name = "name")
    val name: String? = null
}