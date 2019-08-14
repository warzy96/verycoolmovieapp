package com.example.movieapp.data

interface MovieRepository {

    fun getMovies(movieObserver : MovieObserver)

}