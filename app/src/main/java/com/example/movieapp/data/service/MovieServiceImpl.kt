package com.example.movieapp.data.service

import android.graphics.BitmapFactory
import com.example.movieapp.data.api.MovieApiFactory
import com.example.movieapp.data.api.PosterApiFactory
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.callback.PosterCallback
import com.example.movieapp.data.mapper.ApiMapperImpl
import com.example.movieapp.data.model.GenreResults
import com.example.movieapp.data.model.MovieResults
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class MovieServiceImpl : MovieService {

    override fun getMovies(movieCallback: MovieCallback) {
        val call = MovieApiFactory.getApi().getMovies(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<MovieResults> {

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                if (response.isSuccessful ) {
                    movieCallback.onMoviesFetched(mapper.map(response.body()?.results ?: listOf()))
                }
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
            }

        })
    }

    override fun getPoster(posterPath: String, posterCallback: PosterCallback) {
        val call = PosterApiFactory.getApi().getPoster(posterPath)

        call.enqueue(object : retrofit2.Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful ) {
                    val bitmap = BitmapFactory.decodeStream(response.body()?.byteStream())
                    posterCallback.onPosterFetched(bitmap)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

        })
    }

    override fun getGenres(genresCallback: GenresCallback) {
        val call = MovieApiFactory.getApi().getGenres(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<GenreResults> {

            override fun onResponse(call: Call<GenreResults>, response: Response<GenreResults>) {
                if (response.isSuccessful ) {
                    genresCallback.onGenresFetched(response.body()?.genres ?: listOf())
                }
            }

            override fun onFailure(call: Call<GenreResults>, t: Throwable) {
            }

        })
    }

    companion object {
        val mapper = ApiMapperImpl()
    }
}