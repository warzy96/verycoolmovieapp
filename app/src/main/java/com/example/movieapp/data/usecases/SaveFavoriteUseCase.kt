package com.example.movieapp.data.usecases

import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.usecases.types.CompletableUseCase
import com.example.movieapp.ui.view.model.MovieViewModel
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject

class SaveFavoriteUseCase : CompletableUseCase<MovieViewModel>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun execute(param: MovieViewModel): Completable {
        return repository.saveFavorite(viewModelMapper.mapMovieViewModelToMovie(param))
    }
}