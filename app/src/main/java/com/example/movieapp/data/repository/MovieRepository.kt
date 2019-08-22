package com.example.movieapp.data.repository

import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieCallback

interface MovieRepository {

    fun getMovies(movieCallback: MovieCallback)

    fun getGenres(genresCallback: GenresCallback)
}