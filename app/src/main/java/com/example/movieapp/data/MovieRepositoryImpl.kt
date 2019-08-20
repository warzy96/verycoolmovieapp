package com.example.movieapp.data

class MovieRepositoryImpl(val movieSevice: MovieService) : MovieRepository {

    override fun getMovies(movieObserver: MovieCallback) {
        return movieSevice.getMovies(movieObserver)
    }
}