package com.example.movieapp.domain.usecases

import com.example.movieapp.ui.view.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.domain.usecases.types.CompletableUseCase
import com.example.movieapp.ui.view.model.MovieViewModel
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class SaveFavoriteUseCase : CompletableUseCase<MovieViewModel>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun execute(param: MovieViewModel): Completable {
        return repository
            .saveFavorite(viewModelMapper.mapMovieViewModelToMovie(param))
            .subscribeOn(Schedulers.io())
    }
}