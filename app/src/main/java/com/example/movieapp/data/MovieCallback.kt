package com.example.movieapp.data

import com.example.movieapp.domain.Movie

interface MovieCallback {

    fun moviesUpdated(movies : List<Movie>)
}