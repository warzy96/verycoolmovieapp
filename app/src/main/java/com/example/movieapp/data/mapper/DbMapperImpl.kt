package com.example.movieapp.data.mapper

import com.example.movieapp.data.dao.DbGenre
import com.example.movieapp.data.dao.DbMovieDetails
import com.example.movieapp.data.dao.DbProductionCountry
import com.example.movieapp.data.dao.MovieDatabase
import com.example.movieapp.domain.Genre
import com.example.movieapp.domain.MovieDetails
import com.example.movieapp.domain.ProductionCountry
import org.koin.core.KoinComponent
import org.koin.core.inject

class DbMapperImpl : DbMapper, KoinComponent {

    private val movieDb: MovieDatabase by inject()

    override fun mapMovieDetailsToDbMovieDetails(movieDetails: MovieDetails) = DbMovieDetails(
        id = movieDetails.id,
        title = movieDetails.title,
        voteAverage = movieDetails.voteAverage,
        voteCount = movieDetails.voteCount,
        popularity = movieDetails.popularity,
        posterPath = movieDetails.posterPath,
        backdropPath = movieDetails.backdropPath,
        originalLanguage = movieDetails.originalLanguage,
        originalTitle = movieDetails.originalTitle,
        isAdult = movieDetails.isAdult,
        overview = movieDetails.overview,
        releaseDate = movieDetails.releaseDate,
        runtime = movieDetails.runtime,
        homepage = movieDetails.homepage,
        tagline = movieDetails.tagline
    )

    override fun mapDbMovieDetailsToMovieDetails(movieDetails: DbMovieDetails) = MovieDetails(
        id = movieDetails.id,
        title = movieDetails.title,
        voteAverage = movieDetails.voteAverage,
        voteCount = movieDetails.voteCount,
        popularity = movieDetails.popularity,
        posterPath = movieDetails.posterPath,
        backdropPath = movieDetails.backdropPath,
        originalLanguage = movieDetails.originalLanguage,
        originalTitle = movieDetails.originalTitle,
        genres = mapDbGenresToGenres(movieDb.movieGenreJoinDao().getGenresForMovie(movieDetails.id)),
        countries = mapDbProductionCountriesToProductionCountries(movieDb.movieCountryJoinDao().getCountriesForMovie(movieDetails.id)),
        isAdult = movieDetails.isAdult,
        overview = movieDetails.overview,
        releaseDate = movieDetails.releaseDate,
        runtime = movieDetails.runtime,
        homepage = movieDetails.homepage,
        tagline = movieDetails.tagline
    )

    private fun mapGenreToDbGenre(genre: Genre) = DbGenre(
        id = genre.id,
        name = genre.name
    )

    private fun mapDbGenreToGenre(genre: DbGenre) = Genre(
        id = genre.id,
        name = genre.name
    )

    override fun mapGenresToDbGenres(genres: List<Genre>) = genres.map { mapGenreToDbGenre(it) }

    override fun mapDbGenresToGenres(genres: List<DbGenre>) = genres.map { mapDbGenreToGenre(it) }

    private fun mapProductionCountrieToDbProductionCountrie(productionCountry: ProductionCountry) = DbProductionCountry(
        isoCode = productionCountry.isoCode,
        name = productionCountry.name
    )

    private fun mapDbProductionCountrieToProductionCountrie(productionCountry: DbProductionCountry) = ProductionCountry(
        isoCode = productionCountry.isoCode,
        name = productionCountry.name
    )

    override fun mapProductionCountriesToDbProductionCountries(productionCountries: List<ProductionCountry>) = productionCountries.map {
        mapProductionCountrieToDbProductionCountrie(it)
    }

    override fun mapDbProductionCountriesToProductionCountries(productionCountries: List<DbProductionCountry>) = productionCountries.map {
        mapDbProductionCountrieToProductionCountrie(it)
    }
}