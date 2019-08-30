package com.example.movieapp.data.service

import android.util.Log
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.data.dao.MovieCountryJoin
import com.example.movieapp.data.dao.MovieDatabase
import com.example.movieapp.data.dao.MovieGenreJoin
import com.example.movieapp.data.mapper.ApiMapper
import com.example.movieapp.data.mapper.DbMapper
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieServiceImpl : MovieService, KoinComponent {

    private val apiMapper: ApiMapper by inject()
    private val dbMapper: DbMapper by inject()
    private val movieApi: MovieApi by inject()
    private val movieDb: MovieDatabase by inject()

    override fun save(movieDetails: MovieDetails) {
        val movieGenreJoins = mutableListOf<MovieGenreJoin>()
        val movieCountryJoins = mutableListOf<MovieCountryJoin>()

        for (genre in movieDetails.genres) {
            movieGenreJoins.add(MovieGenreJoin(movieDetails.id, genre.id))
        }

        for (county in movieDetails.countries) {
            movieCountryJoins.add(MovieCountryJoin(movieDetails.id, county.isoCode))
        }

        try {
            movieDb.movieDetailsDao().insert(dbMapper.mapMovieDetailsToDbMovieDetails(movieDetails))
            movieDb.productionCountryDao().insert(dbMapper.mapProductionCountriesToDbProductionCountries(movieDetails.countries))
            movieDb.genreDao().insert(dbMapper.mapGenresToDbGenres(movieDetails.genres))
            movieDb.movieGenreJoinDao().insert(movieGenreJoins).size.toString()
            movieDb.movieCountryJoinDao().insert(movieCountryJoins).size.toString()
        } catch (e: Exception) {
            Log.e("database", e.localizedMessage)
        }
    }

    override fun getMovies() =
        movieApi
            .getMovies(MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMoviesSearchResult(query: String): Single<List<Movie>> =
        movieApi
            .getMoviesSearchResult(query, MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMoviesSearchResult(page: Int, query: String): Single<List<Movie>> =
        movieApi
            .getMoviesSearchResult(page, query, MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMovies(page: Int) =
        movieApi
            .getMovies(page, MovieApi.API_KEY)
            .map { apiMapper.mapApiMoviesToMovies(it.results ?: listOf()) }

    override fun getMovie(movieId: Int): Single<MovieDetails> {
        return movieDb.movieDetailsDao().loadById(movieId)
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                movieApi
                    .getMovie(movieId, MovieApi.API_KEY)
                    .map(apiMapper::mapApiMovieDetailsToMovieDetails)
                    .doOnSuccess({ save(it) })
                    .map(dbMapper::mapMovieDetailsToDbMovieDetails)
                    .blockingGet()
            }
            .map(dbMapper::mapDbMovieDetailsToMovieDetails)
    }
}