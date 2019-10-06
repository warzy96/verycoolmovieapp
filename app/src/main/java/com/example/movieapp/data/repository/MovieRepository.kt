package com.example.movieapp.data.repository

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {

    fun saveFavorite(movie: Movie): Completable

    fun removeFavorite(movie: Movie): Completable

    fun getFavorites(): Single<List<Movie>>

    fun getFavorite(movieId: Int): Single<Movie>

    fun save(movieDetails: MovieDetails): Completable

    fun getMovies(): Single<List<Movie>>

    fun getMovies(page: Int): Single<List<Movie>>

    fun getMoviesSearchResult(query: String): Single<List<Movie>>

    fun getMoviesSearchResult(page: Int, query: String): Single<List<Movie>>

    fun getMovie(movieId: Int): Single<MovieDetails>
}