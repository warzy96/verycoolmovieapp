package com.example.movieapp.data

import com.example.movieapp.data.mapper.ApiMapperImpl
import com.example.movieapp.data.mapper.ViewMapperImpl
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.service.MovieService
import com.example.movieapp.data.service.MovieServiceImpl

class DependencyInjector {

    companion object {
        private val movieService = MovieServiceImpl()
        private val movieRepository = MovieRepositoryImpl(getService())
        private val apiMapper = ApiMapperImpl()
        private val viewMapper = ViewMapperImpl()

        fun getRepository(): MovieRepository = movieRepository

        fun getService(): MovieService = movieService

        fun getApiMapper() = apiMapper

        fun getViewMapper() = viewMapper
    }
}