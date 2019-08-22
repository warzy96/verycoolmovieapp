package com.example.movieapp.data.service

import com.example.movieapp.data.api.MovieApiFactory
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.GenreResults
import com.example.movieapp.data.api.model.MovieResults
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.callback.MovieDetailsCallback
import com.example.movieapp.ui.MovieApplication.Companion.dependencyInjector
import retrofit2.Call
import retrofit2.Response

class MovieServiceImpl : MovieService {

    override fun getMovies(movieCallback: MovieCallback) {
        val call = MovieApiFactory.getApi().getMovies(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<MovieResults> {

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                if (response.isSuccessful) {
                    movieCallback.onMoviesFetched(dependencyInjector.getApiMapper().mapApiMoviesToMovies(response.body()?.results ?: listOf()))
                } else {
                    movieCallback.onError(RuntimeException("Unable to fetch movies."))
                }
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                movieCallback.onError(t)
            }
        })
    }

    override fun getMovie(movieId: Int, movieDetailsCallback: MovieDetailsCallback) {
        val call = MovieApiFactory.getApi().getMovie(movieId, MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<ApiMovieDetails> {

            override fun onResponse(call: Call<ApiMovieDetails>, response: Response<ApiMovieDetails>) {
                if (response.isSuccessful) {
                    movieDetailsCallback
                        .onMovieDetailsFetched(dependencyInjector.getApiMapper().mapApiMovieDetailsToMovie(response.body() ?: ApiMovieDetails()))
                } else {
                    movieDetailsCallback.onError(RuntimeException("Unable to fetch movie details."))
                }
            }

            override fun onFailure(call: Call<ApiMovieDetails>, t: Throwable) {
                movieDetailsCallback.onError(t)
            }
        })
    }

    override fun getGenres(genresCallback: GenresCallback) {
        val call = MovieApiFactory.getApi().getGenres(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<GenreResults> {

            override fun onResponse(call: Call<GenreResults>, response: Response<GenreResults>) {
                if (response.isSuccessful) {
                    genresCallback.onGenresFetched(response.body()?.genres ?: listOf())
                } else {
                    genresCallback.onError(RuntimeException("Unable to fetch genres."))
                }
            }

            override fun onFailure(call: Call<GenreResults>, t: Throwable) {
                genresCallback.onError(t)
            }
        })
    }
}