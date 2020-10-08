package com.example.searchmovies

import android.app.Application
import com.example.searchmovies.di.networkModule
import com.example.searchmovies.di.repositoryModule
import com.example.searchmovies.di.viewModelModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class SearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            loadKoinModules(listOf(viewModelModule,networkModule,repositoryModule))
        }
    }
}