package com.example.movieapp.data.api

import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiMovieResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") api_key: String): Single<ApiMovieResults>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movie_id: Int, @Query("api_key") api_key: String): Single<ApiMovieDetails>
}