package com.example.movieapp.data.mapper

import com.example.movieapp.data.model.ApiMovie
import com.example.movieapp.domain.Movie

class ApiMapperImpl : ApiMapper {

    override fun mapApiMoviesToMovies(apiMovies: List<ApiMovie>) = apiMovies.map {
        mapApiMovieToMovie(it)
    }

    companion object {
        private const val SCORE_DIVIDER = 2

        private fun mapApiMovieToMovie(apiMovie: ApiMovie) = Movie(
            title = apiMovie.title ?: "",
            voteAverage = apiMovie.voteAverage?.div(SCORE_DIVIDER) ?: 0.0, // mapping 10-point score to 5-star rating
            voteCount = apiMovie.voteCount ?: 0,
            popularity = apiMovie.popularity ?: 0.0,
            posterPath = apiMovie.posterPath ?: "",
            originalLanguage = apiMovie.originalLanguage ?: "",
            originalTitle = apiMovie.originalTitle ?: "",
            genreIds = apiMovie.genreIds ?: apiMovie.genreIds ?: listOf(),
            isAdult = apiMovie.isAdult ?: false,
            overview = apiMovie.overview ?: "",
            releaseDate = apiMovie.releaseDate ?: ""
        )
    }
}