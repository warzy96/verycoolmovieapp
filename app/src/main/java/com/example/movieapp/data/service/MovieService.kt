package com.example.movieapp.data.service

import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.callback.PosterCallback

interface MovieService {

    fun getMovies(movieCallback: MovieCallback)
    fun getGenres(genresCallback: GenresCallback)
    fun getPoster(posterPath: String, posterCallback: PosterCallback)
}