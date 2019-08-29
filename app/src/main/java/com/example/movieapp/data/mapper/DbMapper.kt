package com.example.movieapp.data.mapper

import com.example.movieapp.data.dao.DbMovieDetails
import com.example.movieapp.domain.*

interface DbMapper {

    fun mapMovieDetailsToDbMovieDetails(movieDetails: MovieDetails): DbMovieDetails

    fun mapGenresToDbGenres(genres: List<Genre>): List<DbGenre>

    fun mapProductionCountriesToDbProductionCountries(productionCountries: List<ProductionCountry>): List<DbProductionCountry>
}