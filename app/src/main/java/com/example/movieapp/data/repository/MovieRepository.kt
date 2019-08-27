package com.example.movieapp.data.repository

import com.example.movieapp.data.service.callback.GenresCallback
import com.example.movieapp.data.service.callback.MovieCallback
import com.example.movieapp.data.service.callback.MovieDetailsCallback

interface MovieRepository {

    fun getMovies(movieCallback: MovieCallback)

    fun getMovie(movieId: Int, movieDetailsCallback: MovieDetailsCallback)

    fun getGenres(genresCallback: GenresCallback)
}