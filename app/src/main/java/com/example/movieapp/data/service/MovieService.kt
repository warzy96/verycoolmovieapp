package com.example.movieapp.data.service

import com.example.movieapp.data.service.callback.GenresCallback
import com.example.movieapp.data.service.callback.MovieCallback
import com.example.movieapp.data.service.callback.MovieDetailsCallback

interface MovieService {

    fun getMovies(movieCallback: MovieCallback)

    fun getMovie(movieId: Int, movieDetailsCallback: MovieDetailsCallback)

    fun getGenres(genresCallback: GenresCallback)
}