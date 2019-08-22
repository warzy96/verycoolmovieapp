package com.example.movieapp.ui

import android.app.Application
import com.example.movieapp.data.DependencyInjector

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        dependencyInjector = DependencyInjector(this)
    }

    companion object {
        lateinit var dependencyInjector: DependencyInjector
    }

}