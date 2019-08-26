package com.example.movieapp.domain

import java.io.Serializable

data class ProductionCountry(

    val isoCode: String,
    val name: String
) : Serializable