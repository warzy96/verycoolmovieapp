package com.example.movieapp.data.service

import com.example.movieapp.data.api.MovieApiFactory
import com.example.movieapp.data.mapper.ApiMapper
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieServiceImpl : MovieService, KoinComponent {

    private val apiMapper: ApiMapper by inject()

    override fun getMovies() =
        MovieApiFactory.getApi().getMovies(MovieApiFactory.API_KEY).map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMovies(page:Int) =
        MovieApiFactory.getApi().getMovies(page, MovieApiFactory.API_KEY).map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMovie(movieId: Int) =
        MovieApiFactory.getApi().getMovie(movieId, MovieApiFactory.API_KEY).map { apiMapper.mapApiMovieDetailsToMovieDetails(it) }
}