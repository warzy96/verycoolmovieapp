package com.example.movieapp.data

import com.example.movieapp.domain.Movie

interface MovieObserver {

    fun moviesUpdated(movies : List<Movie>)

}