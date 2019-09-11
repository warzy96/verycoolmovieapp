package com.example.movieapp.ui

import android.app.Application
import com.bumptech.glide.Glide
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.data.dao.MovieDatabase
import com.example.movieapp.data.mapper.*
import com.example.movieapp.ui.favorites.presenter.FavoritesListPresenter
import com.example.movieapp.ui.moviedetails.presenter.MovieDetailsPresenter
import com.example.movieapp.ui.movies.presenter.MovieListPresenter
import com.example.movieapp.ui.router.MovieListRouter
import com.example.movieapp.ui.router.MovieListRouterImpl
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.service.MovieService
import com.example.movieapp.data.service.MovieServiceImpl
import com.example.movieapp.data.usecases.*
import com.example.movieapp.data.util.ImageLoader
import com.example.movieapp.data.util.ImageLoaderImpl
import com.example.movieapp.ui.activities.MainActivity
import com.example.movieapp.ui.favorites.fragments.FavoritesFragment
import com.example.movieapp.ui.moviedetails.fragments.MovieDetailsFragment
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
        single { DbMapperImpl() as DbMapper }
        single { ViewModelMapperImpl() as ViewModelMapper }
        single { getRetrofit().create(MovieApi::class.java) as MovieApi }
        single { MovieDatabase.getDB(applicationContext, "movie-db") }
        single { GetMovieDetailsUseCase() }
        single { GetMoviesUseCase() }
        single { GetMoviesSearchUseCase() }
        single { MovieListRouterImpl() as MovieListRouter }
        single { SaveFavoriteUseCase() }
        single { RemoveFavoriteUseCase() }
        single { GetFavoritesUseCase() }

        scope(named<MainActivity>()) {
            scoped { MovieListPresenter() }
        }
        scope(named<MovieDetailsFragment>()) {
            scoped { MovieDetailsPresenter() }
        }
        scope(named<FavoritesFragment>()) {
            scoped { FavoritesListPresenter() }
        }
    }

    fun getRetrofit() = Retrofit.Builder()
        .baseUrl(MovieApi.API_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()
}