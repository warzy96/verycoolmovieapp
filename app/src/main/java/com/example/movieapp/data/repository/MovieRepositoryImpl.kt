package com.example.movieapp.data.repository

import com.example.movieapp.data.service.MovieService
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieRepositoryImpl : MovieRepository, KoinComponent {

    private val movieService: MovieService by inject()

    override fun saveFavorite(movie: Movie) = movieService.saveFavorite(movie)

    override fun removeFavorite(movie: Movie) = movieService.removeFavorite(movie)

    override fun getFavorites() = movieService.getFavorites()

    override fun getFavorite(movieId: Int) = movieService.getFavorite(movieId)

    override fun save(movieDetails: MovieDetails) = movieService.saveMovie(movieDetails)

    override fun getMovies(sort: String) = movieService.getMovies(sort)

    override fun getMovies(sort: String, page: Int) = movieService.getMovies(sort, page)

    override fun getMoviesSearchResult(query: String): Single<List<Movie>> = movieService.getMoviesSearchResult(query)

    override fun getMoviesSearchResult(page: Int, query: String): Single<List<Movie>> = movieService.getMoviesSearchResult(page, query)

    override fun getMovie(movieId: Int) = movieService.getMovie(movieId)
}