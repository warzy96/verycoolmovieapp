package com.example.movieapp.data.api

import okhttp3.OkHttpClient
import retrofit2.MoshiConverterFactory
import retrofit2.Retrofit

class RetrofitFactory {

    companion object {
        fun getRetrofit(baseUrl: String) : Retrofit {

            val httpClient = OkHttpClient.Builder()

            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                    MoshiConverterFactory.create()
                )

            val retrofit = builder
                .client(
                    httpClient.build()
                )
                .build()

            return retrofit
        }
    }
}