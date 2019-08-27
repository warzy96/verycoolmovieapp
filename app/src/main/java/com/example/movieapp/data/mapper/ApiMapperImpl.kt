package com.example.movieapp.data.mapper

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.api.model.ApiMovie
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiProductionCountry
import com.example.movieapp.data.mapper.MapperConstants.Companion.DEFAULT_BOOLEAN
import com.example.movieapp.data.mapper.MapperConstants.Companion.DEFAULT_FLOAT
import com.example.movieapp.data.mapper.MapperConstants.Companion.DEFAULT_ID
import com.example.movieapp.data.mapper.MapperConstants.Companion.DEFAULT_INT
import com.example.movieapp.data.mapper.MapperConstants.Companion.DEFAULT_STRING
import com.example.movieapp.domain.Genre
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import com.example.movieapp.domain.ProductionCountry

class ApiMapperImpl : ApiMapper {

    override fun mapApiMoviesToMovies(apiMovies: List<ApiMovie>) = apiMovies.map { mapApiMovieToMovie(it) }

    companion object {
        private const val SCORE_DIVIDER = 2
        private const val POSTER_API_URL = "https://image.tmdb.org/t/p/original/"
    }

    private fun mapApiMovieToMovie(apiMovie: ApiMovie) = Movie(
        title = apiMovie.title ?: DEFAULT_STRING,
        voteAverage = apiMovie.voteAverage?.div(SCORE_DIVIDER) ?: DEFAULT_FLOAT, // mapping 10-point score to 5-star rating
        voteCount = apiMovie.voteCount ?: DEFAULT_INT,
        posterPath = POSTER_API_URL + apiMovie.posterPath,
        id = apiMovie.id ?: DEFAULT_ID,
        releaseDate = apiMovie.releaseDate ?: DEFAULT_STRING
    )

    private fun mapApiGenreToGenre(apiGenre: ApiGenre) = Genre(
        id = apiGenre.id ?: DEFAULT_ID,
        name = apiGenre.name ?: DEFAULT_STRING
    )

    private fun mapApiProductionCountryToProductionCountry(apiProductionCountry: ApiProductionCountry) = ProductionCountry(
        isoCode = apiProductionCountry.isoCode ?: DEFAULT_STRING,
        name = apiProductionCountry.name ?: DEFAULT_STRING
    )

    override fun mapApiMovieDetailsToMovieDetails(apiMovieDetails: ApiMovieDetails) = MovieDetails(
        id = apiMovieDetails.id ?: DEFAULT_ID,
        title = apiMovieDetails.title ?: DEFAULT_STRING,
        voteAverage = apiMovieDetails.voteAverage?.div(SCORE_DIVIDER) ?: DEFAULT_FLOAT, // mapping 10-point score to 5-star rating
        voteCount = apiMovieDetails.voteCount ?: DEFAULT_INT,
        popularity = apiMovieDetails.popularity ?: DEFAULT_FLOAT,
        posterPath = POSTER_API_URL + apiMovieDetails.posterPath,
        backdropPath = POSTER_API_URL + apiMovieDetails.backdropPath,
        originalLanguage = apiMovieDetails.originalLanguage ?: DEFAULT_STRING,
        originalTitle = apiMovieDetails.originalTitle ?: DEFAULT_STRING,
        genres = mapApiGenresToGenres(apiMovieDetails.genres ?: listOf()),
        countries = mapApiProductionCountriesToProductionCountries(apiMovieDetails.countries ?: listOf()),
        isAdult = apiMovieDetails.isAdult ?: DEFAULT_BOOLEAN,
        overview = apiMovieDetails.overview ?: DEFAULT_STRING,
        releaseDate = apiMovieDetails.releaseDate ?: DEFAULT_STRING,
        runtime = apiMovieDetails.runtime,
        homepage = apiMovieDetails.homepage,
        tagline = apiMovieDetails.tagline
    )

    override fun mapApiGenresToGenres(apiGenres: List<ApiGenre>) = apiGenres.map { mapApiGenreToGenre(it) }

    override fun mapApiProductionCountriesToProductionCountries(apiProductionCountries: List<ApiProductionCountry>) = apiProductionCountries.map {
        mapApiProductionCountryToProductionCountry(it)
    }
}