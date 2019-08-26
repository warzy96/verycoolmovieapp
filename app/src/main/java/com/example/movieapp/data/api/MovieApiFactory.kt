package com.example.movieapp.data.api

class MovieApiFactory {

    companion object {
        private const val API_BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "9007d6f531eed726eb102e488cf9b29b"

        fun getApi(): MovieApi {
            return RetrofitFactory
                .getRetrofit(API_BASE_URL).create(MovieApi::class.java)
        }
    }
}