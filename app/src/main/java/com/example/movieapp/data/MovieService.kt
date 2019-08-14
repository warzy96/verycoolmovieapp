package com.example.movieapp.data

interface MovieService {

    fun getMovies(movieObserver: MovieObserver)

}