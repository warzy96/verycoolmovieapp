package com.example.movieapp.data.mapper

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.api.model.ApiMovie
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiProductionCountry
import com.example.movieapp.domain.Genre
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import com.example.movieapp.domain.ProductionCountry

interface ApiMapper {

    fun mapApiMoviesToMovies(apiMovies: List<ApiMovie>): List<Movie>

    fun mapApiGenresToGenres(apiGenres: List<ApiGenre>): List<Genre>

    fun mapApiProductionCountriesToProductionCountries(apiProductionCountries: List<ApiProductionCountry>): List<ProductionCountry>

    fun mapApiMovieDetailsToMovieDetails(apiMovieDetails: ApiMovieDetails): MovieDetails
}