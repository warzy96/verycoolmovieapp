package com.example.movieapp.data.api

import com.example.movieapp.data.model.GenreResults
import com.example.movieapp.data.model.MovieResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") api_key : String): Call<MovieResults>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") api_key : String): Call<GenreResults>
}