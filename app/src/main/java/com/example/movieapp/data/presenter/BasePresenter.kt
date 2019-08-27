package com.example.movieapp.data.presenter

interface BasePresenter<T> {

    fun onDestroy()

    fun setView(view: T)
}