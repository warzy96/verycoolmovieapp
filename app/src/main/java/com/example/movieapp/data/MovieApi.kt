package com.example.movieapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") api_key : String): Call<MovieResults>

}