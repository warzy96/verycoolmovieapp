package com.example.movieapp.data.usecases

import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.usecases.types.SingleUseCase
import com.example.movieapp.ui.view.model.MovieViewModel
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetFavoritesUseCase : SingleUseCase<List<MovieViewModel>>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun execute(): Single<List<MovieViewModel>> =
        repository
            .getFavorites()
            .map(viewModelMapper::mapMoviesToMovieViewModels)
}