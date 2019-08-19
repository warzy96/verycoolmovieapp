package com.example.movieapp.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PosterApi {

    @GET("{image}")
    fun getPoster(@Path("image") image: String) : Call<ResponseBody>
}