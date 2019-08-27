package com.example.movieapp.data.repository

import com.example.movieapp.data.service.MovieService
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.observers.DisposableSingleObserver

class MovieRepositoryImpl(val movieSevice: MovieService) : MovieRepository {

    override fun getMovies(moviesObserver: DisposableSingleObserver<List<Movie>>) = movieSevice.getMovies(moviesObserver)

    override fun getMovie(movieId: Int, movieObserver: DisposableSingleObserver<MovieDetails>) = movieSevice.getMovie(movieId, movieObserver)
}