package com.example.movieapp.data.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T> : LifecycleAwarePresenter {

    protected val composite = CompositeDisposable()

    override fun onStop() {
        composite.dispose()
    }

    abstract fun setView(view: T)
}