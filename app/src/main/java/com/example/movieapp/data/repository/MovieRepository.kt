package com.example.movieapp.data.repository

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.observers.DisposableSingleObserver

interface MovieRepository {

    fun getMovies(moviesObserver: DisposableSingleObserver<List<Movie>>)

    fun getMovies(page: Int, moviesObserver: DisposableSingleObserver<List<Movie>>)

    fun getMovie(movieId: Int, movieObserver: DisposableSingleObserver<MovieDetails>)
}