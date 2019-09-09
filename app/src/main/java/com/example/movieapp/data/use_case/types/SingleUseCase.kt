package com.example.movieapp.data.use_case.types

import io.reactivex.Single

interface SingleUseCase<T> {

    fun execute(): Single<T>
}