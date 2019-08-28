package com.example.movieapp.data.repository

import com.example.movieapp.data.service.MovieService
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieRepositoryImpl : MovieRepository, KoinComponent {

    private val movieService: MovieService by inject()

    override fun getMovies() = movieService.getMovies()

    override fun getMovie(movieId: Int) = movieService.getMovie(movieId)
}