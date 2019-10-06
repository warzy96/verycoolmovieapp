package com.example.movieapp.data.use_case.types

import io.reactivex.Observable

interface ObservableUseCase<T, U> {

    fun execute(param: U): Observable<T>
}