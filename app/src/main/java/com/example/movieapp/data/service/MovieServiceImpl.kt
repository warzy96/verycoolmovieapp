package com.example.movieapp.data.service

import com.example.movieapp.data.api.MovieApiFactory
import com.example.movieapp.data.api.model.ApiGenreResults
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiMovieResults
import com.example.movieapp.data.service.callback.GenresCallback
import com.example.movieapp.data.service.callback.MovieCallback
import com.example.movieapp.data.service.callback.MovieDetailsCallback
import com.example.movieapp.ui.MovieApplication
import retrofit2.Call
import retrofit2.Response

class MovieServiceImpl : MovieService {

    private val apiMapper by lazy { MovieApplication.dependencyInjector.provideApiMapper() }

    override fun getMovies(movieCallback: MovieCallback) {
        val call = MovieApiFactory.getApi().getMovies(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<ApiMovieResults> {

            override fun onResponse(call: Call<ApiMovieResults>, response: Response<ApiMovieResults>) {
                if (response.isSuccessful) {
                    movieCallback.onMoviesFetched(apiMapper.mapApiMoviesToMovies(response.body()?.results ?: listOf()))
                } else {
                    movieCallback.onError(RuntimeException("Unable to fetch movies."))
                }
            }

            override fun onFailure(call: Call<ApiMovieResults>, t: Throwable) {
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
                        .onMovieDetailsFetched(apiMapper.mapApiMovieDetailsToMovieDetails(response.body() ?: ApiMovieDetails()))
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

        call.enqueue(object : retrofit2.Callback<ApiGenreResults> {

            override fun onResponse(call: Call<ApiGenreResults>, response: Response<ApiGenreResults>) {
                if (response.isSuccessful) {
                    genresCallback.onGenresFetched(response.body()?.genres ?: listOf())
                } else {
                    genresCallback.onError(RuntimeException("Unable to fetch genres."))
                }
            }

            override fun onFailure(call: Call<ApiGenreResults>, t: Throwable) {
                genresCallback.onError(t)
            }
        })
    }
}