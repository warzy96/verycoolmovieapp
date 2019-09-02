package com.example.movieapp.ui

import android.app.Application
import com.bumptech.glide.Glide
import com.example.movieapp.data.ImageLoader
import com.example.movieapp.data.ImageLoaderImpl
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.data.mapper.ApiMapper
import com.example.movieapp.data.mapper.ApiMapperImpl
import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.mapper.ViewModelMapperImpl
import com.example.movieapp.data.presenter.MovieDetailsPresenter
import com.example.movieapp.data.presenter.MovieListPresenter
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.service.MovieService
import com.example.movieapp.data.service.MovieServiceImpl
import com.example.movieapp.ui.activities.MainActivity
import com.example.movieapp.ui.activities.MovieDetailsActivity
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.MoshiConverterFactory
import retrofit2.Retrofit

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(applicationModule)
        }
    }

    private val applicationModule = module() {
        single { MovieServiceImpl() as MovieService }
        single { MovieRepositoryImpl() as MovieRepository }
        single { ImageLoaderImpl(Glide.with(this@MovieApplication)) as ImageLoader }
        single { ApiMapperImpl() as ApiMapper }
        single { ViewModelMapperImpl() as ViewModelMapper }
        single { getRetrofit().create(MovieApi::class.java) as MovieApi }

        scope(named<MainActivity>()) {
            scoped { MovieListPresenter() }
        }
        scope(named<MovieDetailsActivity>()) {
            scoped { MovieDetailsPresenter() }
        }
    }

    fun getRetrofit() = Retrofit.Builder()
        .baseUrl(MovieApi.API_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()
}