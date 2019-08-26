package com.example.movieapp.data.service.callback

import com.example.movieapp.domain.Movie

interface MovieDetailsCallback {

    fun onMovieDetailsFetched(movie: Movie)

    fun onError(t: Throwable)
}