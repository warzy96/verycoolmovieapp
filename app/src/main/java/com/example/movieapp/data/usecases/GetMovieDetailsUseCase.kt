package com.example.movieapp.data.usecases

import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.usecases.types.SingleUseCaseWithParam
import com.example.movieapp.ui.moviedetails.view.MovieDetailsViewModel
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetMovieDetailsUseCase : SingleUseCaseWithParam<MovieDetailsViewModel, Int>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun execute(movieId: Int): Single<MovieDetailsViewModel> =
        repository
            .getMovie(movieId)
            .map(viewModelMapper::mapMovieDetailsToMovieDetailsViewModel)
}