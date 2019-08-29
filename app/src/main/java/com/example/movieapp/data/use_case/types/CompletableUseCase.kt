package com.example.movieapp.data.use_case.types

import io.reactivex.Completable

interface CompletableUseCase<U> {

    fun execute(param: U): Completable
}