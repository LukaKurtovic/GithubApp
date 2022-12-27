package com.example.githubapp

import android.app.Application
import com.example.githubapp.di.appModule
import com.example.githubapp.di.repositoriesModule
import com.example.githubapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, viewModelsModule, repositoriesModule))
        }
    }
}