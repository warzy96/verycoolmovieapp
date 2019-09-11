package com.example.movieapp.data.usecases.types

import io.reactivex.Observable

interface ObservableUseCase<T, U> {

    fun execute(param: U): Observable<T>
}