package com.example.movieapp.data.mapper

import com.example.movieapp.data.model.ApiMovie
import com.example.movieapp.domain.Movie

class ApiMapperImpl : ApiMapper {

    override fun map(apiMovies: List<ApiMovie>) = apiMovies.map {
        map(it)
    }

    companion object {
        fun map(apiMovie: ApiMovie) = Movie(
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
            releaseDate = apiMovie.releaseDate ?: ""
            )
    }
}