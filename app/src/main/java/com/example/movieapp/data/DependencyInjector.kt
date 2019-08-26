package com.example.movieapp.data

import android.content.Context
import com.bumptech.glide.Glide
import com.example.movieapp.data.mapper.ApiMapperImpl
import com.example.movieapp.data.mapper.ViewModelMapperImpl
import com.example.movieapp.data.presenter.MovieDetailsPresenter
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.service.MovieService
import com.example.movieapp.data.service.MovieServiceImpl

class DependencyInjector(private val context: Context) {

    private val movieService: MovieService = MovieServiceImpl()
    private val movieRepository: MovieRepository
    private val apiMapper = ApiMapperImpl()
    private val viewMapper = ViewModelMapperImpl()
    private val imageLoader = ImageLoaderImpl(Glide.with(context))
    private val movieDetailsPresenter = MovieDetailsPresenter()

    init {
        movieRepository = MovieRepositoryImpl(movieService)
    }

    fun provideRepository(): MovieRepository = movieRepository

    fun provideService(): MovieService = movieService

    fun provideApiMapper() = apiMapper

    fun provideViewMapper() = viewMapper

    fun provideImageLoader() = imageLoader

    fun provideMovieDetailsPresenter() = movieDetailsPresenter
}