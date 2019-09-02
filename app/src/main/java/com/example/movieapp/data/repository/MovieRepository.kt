package com.example.movieapp.data.repository

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.Single

interface MovieRepository {

    fun getMovies(): Single<List<Movie>>

    fun getMovies(page: Int): Single<List<Movie>>

    fun getMovie(movieId: Int): Single<MovieDetails>
}