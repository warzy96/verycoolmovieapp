package com.example.movieapp.data

interface MovieService {

    fun getMovies(movieCallback: MovieCallback)
}