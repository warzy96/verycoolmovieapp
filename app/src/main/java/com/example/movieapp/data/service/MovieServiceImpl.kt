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
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieServiceImpl : MovieService, KoinComponent {

    private val apiMapper: ApiMapper by inject()
    private val dbMapper: DbMapper by inject()
    private val movieApi: MovieApi by inject()
    private val movieDb: MovieDatabase by inject()

    override fun saveFavorite(movie: Movie) = Completable.fromAction {
        try {
            movieDb.movieDao().insert(listOf(dbMapper.mapMovieToDbMovie(movie)))
            getMovie(movie.id)
                .subscribeOn(Schedulers.io())
                .doOnSuccess({ save(it) })
                .subscribe()
                .dispose()
        } catch (e: Exception) {
            Log.e("database", e.localizedMessage)
        }
    }

    override fun removeFavorite(movie: Movie) = Completable.fromAction {
        try {
            movieDb.movieDao().delete(dbMapper.mapMovieToDbMovie(movie))
            movieDb.movieDetailsDao().deleteById(movie.id)
        } catch (e: Exception) {
            Log.e("database", e.localizedMessage)
        }
    }

    override fun save(movieDetails: MovieDetails) = Completable.fromAction {
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
            movieDb.movieGenreJoinDao().insert(movieGenreJoins)
            movieDb.movieCountryJoinDao().insert(movieCountryJoins)
        } catch (e: Exception) {
            Log.e("database", e.localizedMessage)
        }
    }

    override fun getFavorites(): Single<List<Movie>> {
        return movieDb.movieDao().getAll().map(dbMapper::mapDbMoviesToMovies)
    }

    override fun getFavorite(movieId: Int): Single<Movie> {
        return movieDb.movieDao().loadById(movieId).map(dbMapper::mapDbMovieToMovie)
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
        return movieDb.movieGenreJoinDao().getGenresForMovie(movieId).flatMap {
                genres ->
                run {
                    movieDb.movieCountryJoinDao().getCountriesForMovie(movieId)
                        .flatMap{
                            countries ->
                            run {
                                Log.d("countries", countries.size.toString())

                                movieDb.movieDetailsDao().loadById(movieId)
                                    .subscribeOn(Schedulers.io())
                                    .map { dbMapper.mapDbMovieDetailsToMovieDetails(it, dbMapper.mapDbGenresToGenres(genres), dbMapper.mapDbProductionCountriesToProductionCountries(countries)) }
                                    .onErrorReturn {
                                        movieApi
                                            .getMovie(movieId, MovieApi.API_KEY)
                                            .map(apiMapper::mapApiMovieDetailsToMovieDetails)
                                            .blockingGet()
                                    }
                            }
                        }
                }
            }
    }
}