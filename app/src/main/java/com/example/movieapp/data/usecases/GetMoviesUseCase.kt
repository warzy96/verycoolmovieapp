package com.example.movieapp.data.usecases

import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.usecases.types.SingleUseCaseWithTwoParams
import com.example.movieapp.ui.view.model.MovieViewModel
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetMoviesUseCase : SingleUseCaseWithTwoParams<List<MovieViewModel>, Int, String>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun execute(page: Int, sort: String): Single<List<MovieViewModel>> =
        repository
            .getMovies(sort, page)
            .map(viewModelMapper::mapMoviesToMovieViewModels)
}