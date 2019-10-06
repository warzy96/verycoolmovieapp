package com.example.movieapp.data.use_case.types

import io.reactivex.Completable

interface CompletableUseCase<P> {

    fun execute(param: P): Completable
}