package com.example.movieapp.data.service

class MovieServiceProvider {

    companion object {
        private val movieService = MovieServiceImpl()

        fun getService() : MovieService {
            return movieService
        }
    }
}