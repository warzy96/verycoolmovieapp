package com.example.movieapp.data.mapper

import com.example.movieapp.data.api.model.ApiMovie
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.domain.Movie

interface ApiMapper {

    fun mapApiMoviesToMovies(apiMovies: List<ApiMovie>): List<Movie>

    fun mapApiMovieDetailsToMovie(apiMovie: ApiMovieDetails): Movie
}