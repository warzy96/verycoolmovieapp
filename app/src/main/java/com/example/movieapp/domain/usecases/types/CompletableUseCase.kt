package com.example.movieapp.domain.usecases.types

import io.reactivex.Completable

interface CompletableUseCase<P> {

    fun execute(param: P): Completable
}