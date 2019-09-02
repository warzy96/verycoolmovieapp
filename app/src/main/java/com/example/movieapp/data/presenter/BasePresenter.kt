package com.example.movieapp.data.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T> {

    protected val composite = CompositeDisposable()

    open fun onStop() {
        composite.dispose()
    }

    abstract fun setView(view: T)
}