package com.example.movieapp.data

interface MovieRepository {

    fun getMovies(movieCallback: MovieCallback)

}