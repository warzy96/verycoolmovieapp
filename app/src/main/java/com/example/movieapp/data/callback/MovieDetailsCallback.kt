package com.example.movieapp.data.callback

import com.example.movieapp.domain.Movie

interface MovieDetailsCallback {

    fun onMovieDetailsFetched(movie: Movie)

    fun onError()
}