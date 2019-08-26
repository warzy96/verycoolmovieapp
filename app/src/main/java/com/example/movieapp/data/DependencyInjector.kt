package com.example.movieapp.data

import android.content.Context
import com.bumptech.glide.Glide
import com.example.movieapp.data.mapper.ApiMapperImpl
import com.example.movieapp.data.mapper.ViewModelMapperImpl
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.service.MovieService
import com.example.movieapp.data.service.MovieServiceImpl

class DependencyInjector(private val context: Context) {

    private val movieService: MovieService = MovieServiceImpl()
    private var movieRepository: MovieRepository
    private val apiMapper = ApiMapperImpl()
    private val viewMapper = ViewModelMapperImpl()
    private var imageLoader = ImageLoaderImpl(Glide.with(context))

    init {
        movieRepository = MovieRepositoryImpl(movieService)
    }

    fun getRepository(): MovieRepository = movieRepository

    fun getService(): MovieService = movieService

    fun getApiMapper() = apiMapper

    fun getViewMapper() = viewMapper

    fun getImageLoader() = imageLoader
}