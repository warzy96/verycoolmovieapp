package com.example.movieapp.data.mapper

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.api.model.ApiMovie
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiProductionCountry
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
        title = apiMovie.title ?: "",
        voteAverage = apiMovie.voteAverage?.div(SCORE_DIVIDER) ?: 0.0, // mapping 10-point score to 5-star rating
        voteCount = apiMovie.voteCount ?: 0,
        posterPath = POSTER_API_URL + apiMovie.posterPath,
        id = apiMovie.id ?: -1,
        releaseDate = apiMovie.releaseDate ?: ""
    )

    private fun mapApiGenreToGenre(apiGenre: ApiGenre) = Genre(
        id = apiGenre.id ?: -1,
        name = apiGenre.name ?: ""
    )

    private fun mapApiProductionCountryToProductionCountry(apiProductionCountry: ApiProductionCountry) = ProductionCountry(
        isoCode = apiProductionCountry.isoCode ?: "",
        name = apiProductionCountry.name ?: ""
    )

    override fun mapApiMovieDetailsToMovieDetails(apiMovieDetails: ApiMovieDetails) = MovieDetails(
        id = apiMovieDetails.id ?: -1,
        title = apiMovieDetails.title ?: "",
        voteAverage = apiMovieDetails.voteAverage?.div(SCORE_DIVIDER) ?: 0.0, // mapping 10-point score to 5-star rating
        voteCount = apiMovieDetails.voteCount ?: 0,
        popularity = apiMovieDetails.popularity ?: 0.0,
        posterPath = POSTER_API_URL + apiMovieDetails.posterPath,
        backdropPath = POSTER_API_URL + apiMovieDetails.backdropPath,
        originalLanguage = apiMovieDetails.originalLanguage ?: "",
        originalTitle = apiMovieDetails.originalTitle ?: "",
        genres = mapApiGenresToGenres(apiMovieDetails.genres ?: listOf()),
        countries = mapApiProductionCountriesToProductionCountries(apiMovieDetails.countries ?: listOf()),
        isAdult = apiMovieDetails.isAdult ?: false,
        overview = apiMovieDetails.overview ?: "",
        releaseDate = apiMovieDetails.releaseDate ?: "",
        runtime = apiMovieDetails.runtime,
        homepage = apiMovieDetails.homepage,
        tagline = apiMovieDetails.tagline
    )

    override fun mapApiGenresToGenres(apiGenres: List<ApiGenre>) = apiGenres.map { mapApiGenreToGenre(it) }

    override fun mapApiProductionCountriesToProductionCountries(apiProductionCountries: List<ApiProductionCountry>) = apiProductionCountries.map {
        mapApiProductionCountryToProductionCountry(it)
    }
}