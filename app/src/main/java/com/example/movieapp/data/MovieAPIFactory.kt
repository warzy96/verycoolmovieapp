package com.example.movieapp.data

class MovieAPIFactory {

    companion object {

        const val API_BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "9007d6f531eed726eb102e488cf9b29b"

        fun getAPI() : MovieAPI {

            return RetrofitFactory.getRetrofit(API_BASE_URL).create(MovieAPI::class.java)

        }

    }

}