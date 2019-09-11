package com.example.movieapp.data.mapper

import com.example.movieapp.ui.moviedetails.view.MovieDetailsViewModel
import com.example.movieapp.ui.view.model.MovieViewModel
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails

class ViewModelMapperImpl : ViewModelMapper {

    override fun mapMovieViewModelToMovie(movieViewModel: MovieViewModel) = Movie(
        id = movieViewModel.id,
        title = movieViewModel.title,
        voteCount = movieViewModel.voteCount,
        voteAverage = movieViewModel.voteAverage,
        posterPath = movieViewModel.posterPath,
        releaseDate = movieViewModel.releaseDate,
        popularity = movieViewModel.popularity
    )

    override fun mapMoviesToMovieViewModels(movies: List<Movie>) = movies.map { mapMovieToMovieViewModel(it) }

    override fun mapMovieDetailsToMovieDetailsViewModel(movieDetails: MovieDetails) = MovieDetailsViewModel(
        id = movieDetails.id,
        title = movieDetails.title,
        tagline = movieDetails.tagline,
        voteCount = movieDetails.voteCount,
        voteAverage = movieDetails.voteAverage,
        backdropPath = movieDetails.backdropPath,
        posterPath = movieDetails.posterPath,
        originalTitle = movieDetails.originalTitle,
        genres = movieDetails.genres,
        countries = movieDetails.countries,
        isAdult = movieDetails.isAdult,
        overview = movieDetails.overview,
        releaseDate = movieDetails.releaseDate,
        runtime = movieDetails.runtime,
        homepage = movieDetails.homepage
    )

    override fun mapMovieToMovieViewModel(movie: Movie) = mapMovieToMovieViewModel(movie, false)

    override fun mapMovieToMovieViewModel(movie: Movie, isFavorite: Boolean) = MovieViewModel(
        id = movie.id,
        title = movie.title,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount,
        posterPath = movie.posterPath,
        releaseDate = movie.releaseDate,
        favorite = isFavorite,
        popularity = movie.popularity
    )
}