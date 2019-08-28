package com.example.movieapp.data.repository

import com.example.movieapp.data.service.MovieService
import com.example.movieapp.domain.Movie
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieRepositoryImpl : MovieRepository, KoinComponent {

    private val movieService: MovieService by inject()

    override fun getMovies() = movieService.getMovies()

    override fun getMovies(page: Int) = movieService.getMovies(page)

    override fun getMoviesSearchResult(query: String): Single<List<Movie>> = movieService.getMoviesSearchResult(query)

    override fun getMoviesSearchResult(page: Int, query: String): Single<List<Movie>> = movieService.getMoviesSearchResult(page, query)

    override fun getMovie(movieId: Int) = movieService.getMovie(movieId)
}