package com.example.movieapp.data.mapper

import com.example.movieapp.data.model.ApiMovie
import com.example.movieapp.domain.Movie

interface ApiMapper {

    fun map(apiMovies: List<ApiMovie>) : List<Movie>
}