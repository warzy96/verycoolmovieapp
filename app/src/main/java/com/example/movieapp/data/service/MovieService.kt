package com.example.movieapp.data.service

import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.callback.MovieDetailsCallback

interface MovieService {

    fun getMovies(movieCallback: MovieCallback)

    fun getMovie(movieId: Int, movieDetailsCallback: MovieDetailsCallback)

    fun getGenres(genresCallback: GenresCallback)
}