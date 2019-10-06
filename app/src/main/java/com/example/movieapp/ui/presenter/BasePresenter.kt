package com.example.movieapp.ui.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T> : LifecycleAwarePresenter {

    protected var composite = CompositeDisposable()

    override fun onStart() {
        if (composite.isDisposed) {
            composite = CompositeDisposable()
        }
    }

    override fun onStop() {
        composite.dispose()
    }

    abstract fun setView(view: T)
}