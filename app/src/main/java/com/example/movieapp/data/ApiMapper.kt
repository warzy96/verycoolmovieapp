package com.example.movieapp.data

import com.example.movieapp.domain.Movie

interface ApiMapper {

    fun map(apiMovies: List<ApiMovie>) : List<Movie>
}