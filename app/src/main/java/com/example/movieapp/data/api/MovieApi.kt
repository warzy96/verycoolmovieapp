package com.example.movieapp.data.api

import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiMovieResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    companion object {
        const val API_BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "9007d6f531eed726eb102e488cf9b29b"
    }

    @GET("discover/movie")
    fun getMovies(@Query("api_key") api_key: String): Single<ApiMovieResults>

    @GET("search/movie")
    fun getMoviesSearchResult(@Query("query") query: String, @Query("api_key") api_key: String): Single<ApiMovieResults>

    @GET("discover/movie")
    fun getMovies(@Query("page") page: Int, @Query("api_key") api_key: String): Single<ApiMovieResults>

    @GET("search/movie")
    fun getMoviesSearchResult(@Query("page") page: Int, @Query("query") query: String, @Query("api_key") api_key: String): Single<ApiMovieResults>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movie_id: Int, @Query("api_key") api_key: String): Single<ApiMovieDetails>
}