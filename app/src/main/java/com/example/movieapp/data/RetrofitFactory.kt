package com.example.movieapp.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {
        fun getRetrofit(baseUrl: String) : Retrofit {

            val httpClient = OkHttpClient.Builder()

            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                    GsonConverterFactory.create()
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