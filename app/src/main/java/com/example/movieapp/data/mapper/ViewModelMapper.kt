package com.example.movieapp.data.mapper

import com.example.movieapp.ui.moviedetails.view.MovieDetailsViewModel
import com.example.movieapp.ui.view.model.MovieViewModel
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails

interface ViewModelMapper {

    fun mapMovieViewModelToMovie(movieViewModel: MovieViewModel): Movie

    fun mapMoviesToMovieViewModels(movies: List<Movie>): List<MovieViewModel>

    fun mapMovieToMovieViewModel(movie: Movie): MovieViewModel

    fun mapMovieToMovieViewModel(movie: Movie, isFavorite: Boolean): MovieViewModel

    fun mapMovieDetailsToMovieDetailsViewModel(movieDetails: MovieDetails): MovieDetailsViewModel
}