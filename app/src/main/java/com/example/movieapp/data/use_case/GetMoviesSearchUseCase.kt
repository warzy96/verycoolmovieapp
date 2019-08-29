package com.example.movieapp.data.use_case

import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.use_case.requests.SearchMoviesRequest
import com.example.movieapp.data.use_case.types.SingleUseCase
import com.example.movieapp.data.view.model.MovieViewModel
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetMoviesSearchUseCase : SingleUseCase<List<MovieViewModel>, SearchMoviesRequest>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun execute(searchRequest: SearchMoviesRequest): Single<List<MovieViewModel>> = repository
        .getMoviesSearchResult(searchRequest.page, searchRequest.query)
        .map(viewModelMapper::mapMoviesToMovieViewModels)
}