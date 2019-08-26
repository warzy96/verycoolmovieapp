package com.example.movieapp.data.callback

import com.example.movieapp.domain.Movie

interface MovieCallback {

    fun onMoviesFetched(movies: List<Movie>)

    fun onError(t: Throwable)
}