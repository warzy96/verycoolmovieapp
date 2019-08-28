package com.example.movieapp.data.service

import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.data.mapper.ApiMapper
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieServiceImpl : MovieService, KoinComponent {

    private val apiMapper: ApiMapper by inject()
    private val movieApi: MovieApi by inject()

    override fun getMovies() =
        movieApi
            .getMovies(MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMovie(movieId: Int) =
        movieApi
            .getMovie(movieId, MovieApi.API_KEY)
            .map(apiMapper::mapApiMovieDetailsToMovieDetails)
}