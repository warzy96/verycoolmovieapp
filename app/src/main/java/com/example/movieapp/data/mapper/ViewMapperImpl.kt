package com.example.movieapp.data.mapper

import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.domain.Movie

class ViewMapperImpl : ViewMapper {

    override fun mapMoviesToViewMovies(movies: List<Movie>) = movies.map {
        mapMovieToViewMovie(it)
    }

    override fun mapMovieToViewMovie(movie: Movie) = ViewMapperImpl.mapMovieToViewMovie(movie)

    companion object {
        private fun mapMovieToViewMovie(movie: Movie) = ViewMovie(
            id = movie.id,
            title = movie.title,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            posterPath = movie.posterPath,
            originalTitle = movie.originalTitle,
            genreIds = movie.genreIds,
            isAdult = movie.isAdult,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            runtime = movie.runtime,
            homepage = movie.homepage,
            tagline = movie.tagline
        )
    }
}