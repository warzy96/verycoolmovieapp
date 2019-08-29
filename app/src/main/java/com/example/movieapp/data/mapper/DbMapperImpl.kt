package com.example.movieapp.data.mapper

import com.example.movieapp.data.dao.DbMovieDetails
import com.example.movieapp.domain.*

class DbMapperImpl : DbMapper {

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
        genres = mapGenresToDbGenres(movieDetails.genres),
        countries = mapProductionCountriesToDbProductionCountries(movieDetails.countries),
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

    override fun mapGenresToDbGenres(genres: List<Genre>) = genres.map { mapGenreToDbGenre(it) }

    private fun mapProductionCountrieToDbProductionCountrie(productionCountry: ProductionCountry) = DbProductionCountry(
        isoCode = productionCountry.isoCode,
        name = productionCountry.name
    )

    override fun mapProductionCountriesToDbProductionCountries(productionCountries: List<ProductionCountry>) = productionCountries.map {
        mapProductionCountrieToDbProductionCountrie(it)
    }
}