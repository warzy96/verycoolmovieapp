package com.example.movieapp.data.use_case.types

import io.reactivex.Single

interface SingleUseCaseWithTwoParams<T, U, V> {

    fun execute(param1: U, param2: V): Single<T>
}