package com.example.movieapp.data.usecases.types

import io.reactivex.Single

interface SingleUseCase<T> {

    fun execute(): Single<T>
}