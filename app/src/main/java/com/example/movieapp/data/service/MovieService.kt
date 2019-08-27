package com.example.movieapp.data.service

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.observers.DisposableSingleObserver

interface MovieService {

    fun getMovies(moviesObserver: DisposableSingleObserver<List<Movie>>)

    fun getMovie(movieId: Int, movieObserver: DisposableSingleObserver<MovieDetails>)
}