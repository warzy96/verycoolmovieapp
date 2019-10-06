package com.example.movieapp.data.use_case.types

import io.reactivex.Single

interface SingleUseCaseWithParam<T, U> {

    fun execute(param: U): Single<T>
}