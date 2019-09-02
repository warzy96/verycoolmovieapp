package com.example.movieapp.data.service

import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.data.mapper.ApiMapper
import com.example.movieapp.domain.Movie
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieServiceImpl : MovieService, KoinComponent {

    private val apiMapper: ApiMapper by inject()
    private val movieApi: MovieApi by inject()

    override fun getMovies() =
        movieApi
            .getMovies(MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMoviesSearchResult(query: String): Single<List<Movie>> =
        movieApi
            .getMoviesSearchResult(query, MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMoviesSearchResult(page: Int, query: String): Single<List<Movie>> =
        movieApi
            .getMoviesSearchResult(page, query, MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMovies(page: Int) =
        movieApi
            .getMovies(page, MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMovie(movieId: Int) =
        movieApi
            .getMovie(movieId, MovieApi.API_KEY)
            .map(apiMapper::mapApiMovieDetailsToMovieDetails)
}