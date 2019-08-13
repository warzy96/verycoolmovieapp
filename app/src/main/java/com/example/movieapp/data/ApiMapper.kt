package com.example.movieapp.data

import com.example.movieapp.domain.Movie

class ApiMapper {


    companion object {
        fun map(apiMovie: ApiMovie) : Movie{
            return Movie(apiMovie.title!!)
        }

        fun map(apiMovies: List<ApiMovie>) : List<Movie> {
            var list = mutableListOf<Movie>()

            for (apiMovie in apiMovies) {
                list.add(map(apiMovie))
            }

            return list
        }
    }

}