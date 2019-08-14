package com.example.movieapp.data

import com.example.movieapp.domain.Movie

class ApiMapperImpl : ApiMapper {

    override fun map(apiMovies: List<ApiMovie>) = apiMovies.map { Movie(it.title ?: "") }

    companion object {
        fun map(apiMovie: ApiMovie) = Movie(apiMovie.title ?: "")
    }
}