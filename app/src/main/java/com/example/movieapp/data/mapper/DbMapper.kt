package com.example.movieapp.data.mapper

import com.example.movieapp.data.dao.DbGenre
import com.example.movieapp.data.dao.DbMovie
import com.example.movieapp.data.dao.DbMovieDetails
import com.example.movieapp.data.dao.DbProductionCountry
import com.example.movieapp.domain.Genre
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import com.example.movieapp.domain.ProductionCountry

interface DbMapper {

    fun mapMovieToDbMovie(movie: Movie): DbMovie

    fun mapDbMovieToMovie(dbMovie: DbMovie): Movie

    fun mapDbMoviesToMovies(dbMovies: List<DbMovie>): List<Movie>

    fun mapMovieDetailsToDbMovieDetails(movieDetails: MovieDetails): DbMovieDetails

    fun mapDbMovieDetailsToMovieDetails(movieDetails: DbMovieDetails, genres: List<Genre>, countries: List<ProductionCountry>): MovieDetails

    fun mapGenresToDbGenres(genres: List<Genre>): List<DbGenre>

    fun mapDbGenresToGenres(genres: List<DbGenre>): List<Genre>

    fun mapProductionCountriesToDbProductionCountries(productionCountries: List<ProductionCountry>): List<DbProductionCountry>

    fun mapDbProductionCountriesToProductionCountries(productionCountries: List<DbProductionCountry>): List<ProductionCountry>
}