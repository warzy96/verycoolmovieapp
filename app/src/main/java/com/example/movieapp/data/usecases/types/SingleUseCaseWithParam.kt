package com.example.movieapp.data.usecases.types

import io.reactivex.Single

interface SingleUseCaseWithParam<T, U> {

    fun execute(param: U): Single<T>
}