package com.example.movieapp.data.repository

import com.example.movieapp.data.service.callback.GenresCallback
import com.example.movieapp.data.service.callback.MovieCallback
import com.example.movieapp.data.service.callback.MovieDetailsCallback
import com.example.movieapp.data.service.MovieService

class MovieRepositoryImpl(val movieSevice: MovieService) : MovieRepository {

    override fun getMovies(movieCallback: MovieCallback) = movieSevice.getMovies(movieCallback)

    override fun getMovie(movieId: Int, movieDetailsCallback: MovieDetailsCallback) = movieSevice.getMovie(movieId, movieDetailsCallback)

    override fun getGenres(genresCallback: GenresCallback) = movieSevice.getGenres(genresCallback)
}