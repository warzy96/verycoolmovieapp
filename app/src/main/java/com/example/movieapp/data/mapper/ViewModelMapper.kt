package com.example.movieapp.data.mapper

import com.example.movieapp.data.view.model.MovieDetailsViewModel
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails

interface ViewModelMapper {

    fun mapMoviesToMovieViewModels(movies: List<Movie>): List<MovieViewModel>

    fun mapMovieDetailsToMovieDetailsViewModel(movieDetails: MovieDetails): MovieDetailsViewModel
}