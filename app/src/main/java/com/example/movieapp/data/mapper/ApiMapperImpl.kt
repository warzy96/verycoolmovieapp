package com.example.movieapp.data.mapper

import com.example.movieapp.data.api.model.ApiMovie
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.domain.Movie

class ApiMapperImpl : ApiMapper {

    override fun mapApiMoviesToMovies(apiMovies: List<ApiMovie>) = apiMovies.map { mapApiMovieToMovie(it) }

    companion object {
        private const val SCORE_DIVIDER = 2
        private const val POSTER_API_URL = "https://image.tmdb.org/t/p/original/"
    }

    private fun mapApiMovieToMovie(apiMovie: ApiMovie) = Movie(
        title = apiMovie.title ?: "",
        voteAverage = apiMovie.voteAverage?.div(SCORE_DIVIDER) ?: 0.0, // mapping 10-point score to 5-star rating
        voteCount = apiMovie.voteCount ?: 0,
        popularity = apiMovie.popularity ?: 0.0,
        posterPath = POSTER_API_URL + apiMovie.posterPath,
        originalLanguage = apiMovie.originalLanguage ?: "",
        originalTitle = apiMovie.originalTitle ?: "",
        genreIds = apiMovie.genreIds ?: apiMovie.genreIds ?: listOf(),
        isAdult = apiMovie.isAdult ?: false,
        overview = apiMovie.overview ?: "",
        releaseDate = apiMovie.releaseDate ?: "",
        id = apiMovie.id ?: -1,
        homepage = null,
        tagline = null,
        runtime = null
    )

    override fun mapApiMovieDetailsToMovie(apiMovie: ApiMovieDetails) = Movie(
        id = apiMovie.id ?: -1,
        title = apiMovie.title ?: "",
        voteAverage = apiMovie.voteAverage ?: 0.0,
        voteCount = apiMovie.voteCount ?: 0,
        popularity = apiMovie.popularity ?: 0.0,
        posterPath = POSTER_API_URL + apiMovie.posterPath,
        originalLanguage = apiMovie.originalLanguage ?: "",
        originalTitle = apiMovie.originalTitle ?: "",
        genreIds = apiMovie.genres?.map { it.id ?: -1 } ?: listOf(),
        isAdult = apiMovie.isAdult ?: false,
        overview = apiMovie.overview ?: "",
        releaseDate = apiMovie.releaseDate ?: "",
        runtime = apiMovie.runtime,
        homepage = apiMovie.homepage,
        tagline = apiMovie.tagline
    )
}