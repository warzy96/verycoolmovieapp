package com.example.movieapp.data.usecases.types

import io.reactivex.Completable

interface CompletableUseCase<P> {

    fun execute(param: P): Completable
}