package com.example.movieapp.data.service

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.Single

interface MovieService {

    fun save(movieDetails: MovieDetails)

    fun getMovies(): Single<List<Movie>>

    fun getMovies(page: Int): Single<List<Movie>>

    fun getMoviesSearchResult(query: String): Single<List<Movie>>

    fun getMoviesSearchResult(page: Int, query: String): Single<List<Movie>>

    fun getMovie(movieId: Int): Single<MovieDetails>
}