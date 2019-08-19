package com.example.movieapp.data.repository

import com.example.movieapp.data.service.MovieServiceProvider

class MovieRepositoryProvider {

    companion object {
        private val movieRepository = MovieRepositoryImpl(MovieServiceProvider.getService())

        fun getRepository() : MovieRepository {
            return movieRepository
        }
    }
}