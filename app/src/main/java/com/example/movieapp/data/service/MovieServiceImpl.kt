package com.example.movieapp.data.service

import com.example.movieapp.data.api.MovieApiFactory
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.mapper.ApiMapperImpl
import com.example.movieapp.data.model.GenreResults
import com.example.movieapp.data.model.MovieResults
import retrofit2.Call
import retrofit2.Response

class MovieServiceImpl : MovieService {

    override fun getMovies(movieCallback: MovieCallback) {
        val call = MovieApiFactory.getApi().getMovies(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<MovieResults> {

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                if (response.isSuccessful) {
                    movieCallback.onMoviesFetched(mapper.mapApiMoviesToMovies(response.body()?.results ?: listOf()))
                } else {
                    movieCallback.onError()
                }
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                movieCallback.onError()
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
                    genresCallback.onError()
                }
            }

            override fun onFailure(call: Call<GenreResults>, t: Throwable) {
                genresCallback.onError()
            }

        })
    }

    companion object {
        val mapper = ApiMapperImpl()
    }
}