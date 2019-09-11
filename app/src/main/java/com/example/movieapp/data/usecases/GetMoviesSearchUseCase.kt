package com.example.movieapp.data.usecases

import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.usecases.types.SingleUseCaseWithParam
import com.example.movieapp.ui.view.model.MovieViewModel
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetMoviesSearchUseCase : SingleUseCaseWithParam<List<MovieViewModel>, SearchMoviesRequest>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun execute(searchRequest: SearchMoviesRequest): Single<List<MovieViewModel>> =
        repository
            .getMoviesSearchResult(searchRequest.page, searchRequest.query)
            .map(viewModelMapper::mapMoviesToMovieViewModels)
}

data class SearchMoviesRequest(
    val page: Int,
    val query: String
)