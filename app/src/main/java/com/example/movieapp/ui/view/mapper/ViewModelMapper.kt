package com.example.movieapp.ui.view.mapper

import com.example.movieapp.ui.view.model.MovieDetailsViewModel
import com.example.movieapp.ui.view.model.MovieViewModel
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails

interface ViewModelMapper {

    fun mapMovieViewModelToMovie(movieViewModel: MovieViewModel): Movie

    fun mapMoviesToMovieViewModels(movies: List<Movie>): List<MovieViewModel>

    fun mapMovieToMovieViewModel(movie: Movie): MovieViewModel

    fun mapMovieDetailsToMovieDetailsViewModel(movieDetails: MovieDetails): MovieDetailsViewModel
}