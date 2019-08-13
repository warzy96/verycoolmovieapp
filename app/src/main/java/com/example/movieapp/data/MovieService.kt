package com.example.movieapp.data

import com.example.movieapp.domain.Movie

interface MovieService {

    fun getMovies() : List<Movie>

}