package com.example.movieapp.data.mapper

import com.example.movieapp.data.dao.*
import com.example.movieapp.domain.Genre
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import com.example.movieapp.domain.ProductionCountry
import org.koin.core.KoinComponent
import org.koin.core.inject

class DbMapperImpl : DbMapper, KoinComponent {

    private val movieDb: MovieDatabase by inject()

    override fun mapMovieToDbMovie(movie: Movie) = DbMovie(
        id = movie.id,
        title = movie.title,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount,
        posterPath = movie.posterPath,
        releaseDate = movie.releaseDate,
        popularity = movie.popularity
    )

    override fun mapDbMoviesToMovies(dbMovies: List<DbMovie>) = dbMovies.map { mapDbMovieToMovie(it) }

    override fun mapDbMovieToMovie(dbMovie: DbMovie) = Movie(
        id = dbMovie.id,
        title = dbMovie.title,
        voteAverage = dbMovie.voteAverage,
        voteCount = dbMovie.voteCount,
        posterPath = dbMovie.posterPath,
        releaseDate = dbMovie.releaseDate,
        popularity = dbMovie.popularity
    )

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

    override fun mapDbMovieDetailsToMovieDetails(movieDetails: DbMovieDetails, genres: List<Genre>, countries: List<ProductionCountry>) =
        MovieDetails(
            id = movieDetails.id,
            title = movieDetails.title,
            voteAverage = movieDetails.voteAverage,
            voteCount = movieDetails.voteCount,
            popularity = movieDetails.popularity,
            posterPath = movieDetails.posterPath,
            backdropPath = movieDetails.backdropPath,
            originalLanguage = movieDetails.originalLanguage,
            originalTitle = movieDetails.originalTitle,
            genres = genres,
            countries = countries,
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