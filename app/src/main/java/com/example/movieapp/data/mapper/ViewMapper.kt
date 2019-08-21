package com.example.movieapp.data.mapper

import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.domain.Movie

interface ViewMapper {

    fun mapMoviesToViewMovies(movies: List<Movie>): List<ViewMovie>

    fun mapMovieToViewMovie(movie: Movie): ViewMovie
}