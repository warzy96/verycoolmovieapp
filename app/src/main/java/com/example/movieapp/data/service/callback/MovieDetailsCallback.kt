package com.example.movieapp.data.service.callback

import com.example.movieapp.domain.MovieDetails

interface MovieDetailsCallback {

    fun onMovieDetailsFetched(movieDetails: MovieDetails)

    fun onError(t: Throwable)
}