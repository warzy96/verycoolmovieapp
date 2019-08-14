package com.example.movieapp.data

import com.example.movieapp.domain.Movie

class ApiMapper {

    companion object {

        fun map(apiMovie: ApiMovie): Movie {
            return Movie(apiMovie.title ?: "")
        }

        fun map(apiMovies: List<ApiMovie>) = apiMovies.map { Movie(it.title ?: "") }

    }

}