package com.example.movieapp.domain.usecases.types

import io.reactivex.Single

interface SingleUseCaseWithParam<T, U> {

    fun execute(param: U): Single<T>
}