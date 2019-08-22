package com.example.movieapp.data.mapper

import com.example.movieapp.data.api.model.ApiMovie
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.domain.Movie

class ApiMapperImpl : ApiMapper {

    override fun mapApiMoviesToMovies(apiMovies: List<ApiMovie>) = apiMovies.map { mapApiMovieToMovie(it) }

    override fun mapApiMovieDetailsToMovie(apiMovie: ApiMovieDetails) = ApiMapperImpl.mapApiMovieDetailsToMovie(apiMovie)

    companion object {
        private fun mapApiMovieToMovie(apiMovie: ApiMovie) = Movie(
            id = apiMovie.id ?: -1,
            title = apiMovie.title ?: "",
            voteAverage = apiMovie.voteAverage ?: 0.0,
            voteCount = apiMovie.voteCount ?: 0,
            popularity = apiMovie.popularity ?: 0.0,
            posterPath = apiMovie.posterPath ?: "",
            originalLanguage = apiMovie.originalLanguage ?: "",
            originalTitle = apiMovie.originalTitle ?: "",
            genreIds = apiMovie.genreIds ?: apiMovie.genreIds ?: listOf(),
            isAdult = apiMovie.isAdult ?: false,
            overview = apiMovie.overview ?: "",
            releaseDate = apiMovie.releaseDate ?: "",
            runtime = null,
            homepage = null,
            tagline = null
        )

        private fun mapApiMovieDetailsToMovie(apiMovie: ApiMovieDetails) = Movie(
            id = apiMovie.id ?: -1,
            title = apiMovie.title ?: "",
            voteAverage = apiMovie.voteAverage ?: 0.0,
            voteCount = apiMovie.voteCount ?: 0,
            popularity = apiMovie.popularity ?: 0.0,
            posterPath = apiMovie.posterPath ?: "",
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
}