package com.example.movieapp.data.api

class PosterApiFactory {

    companion object {
        const val POSTER_API_URL = "https://image.tmdb.org/t/p/original/"

        fun getApi() : PosterApi {
            return RetrofitFactory
                .getRetrofit(POSTER_API_URL).create(PosterApi::class.java)
        }
    }
}