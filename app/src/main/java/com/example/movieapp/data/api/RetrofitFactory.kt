package com.example.movieapp.data.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.MoshiConverterFactory
import retrofit2.Retrofit

class RetrofitFactory {

    companion object {
        fun getRetrofit(baseUrl: String) = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()
    }
}