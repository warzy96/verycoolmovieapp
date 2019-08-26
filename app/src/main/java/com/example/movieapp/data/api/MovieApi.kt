package com.example.movieapp.data.api

import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiGenreResults
import com.example.movieapp.data.api.model.MovieResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") api_key: String): Call<MovieResults>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movie_id: Int, @Query("api_key") api_key: String): Call<ApiMovieDetails>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") api_key: String): Call<ApiGenreResults>
}