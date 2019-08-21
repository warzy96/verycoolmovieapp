package com.example.movieapp.data.repository

import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.callback.MovieDetailsCallback

interface MovieRepository {

    fun getMovies(movieCallback: MovieCallback)

    fun getMovie(movieId: Int, movieDetailsCallback: MovieDetailsCallback)

    fun getGenres(genresCallback: GenresCallback)
}