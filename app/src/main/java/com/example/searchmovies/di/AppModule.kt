package com.example.searchmovies.di


import com.example.searchmovies.ui.moviedetails.MovieDetailsRepository
import com.example.searchmovies.ui.moviedetails.MovieDetailsViewModel
import com.example.searchmovies.ui.movielist.MovieListRepository
import com.example.searchmovies.ui.movielist.MovieListViewModel
import com.example.searchmovies.webservices.ApiStories
import com.example.searchmovies.webservices.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}

val repositoryModule = module {
    factory { MovieListRepository(get()) }
    factory { MovieDetailsRepository(get()) }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
    return OkHttpClient().newBuilder().addNetworkInterceptor(interceptor).build()
}

fun provideApi(retrofit: Retrofit): ApiStories = retrofit.create(ApiStories::class.java)


