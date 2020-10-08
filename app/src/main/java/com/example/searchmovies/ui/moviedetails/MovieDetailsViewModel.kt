package com.example.searchmovies.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.example.searchmovies.base.BaseViewModel
import com.example.searchmovies.model.ApiResponse
import com.example.searchmovies.model.MovieDetails
import kotlinx.coroutines.Dispatchers

class MovieDetailsViewModel(private val repository: MovieDetailsRepository) : BaseViewModel() {

    var movieId = MutableLiveData<String>()
    var details = MutableLiveData<MovieDetails>()

    private fun getMovieDetails(movieId: String): LiveData<ApiResponse<MovieDetails>> =
        liveData(Dispatchers.IO) {
            emit(ApiResponse.LOADING)
            emit(repository.getMovieDetails(movieId))
            emit(ApiResponse.COMPLETED)
        }

    fun requestMovieDetails(): LiveData<ApiResponse<MovieDetails>> =
        Transformations.switchMap(movieId) {
            getMovieDetails(movieId = it)
        }

    fun setMovieID(movieId: String) {
        this.movieId.value = movieId
    }

    fun setMovieDetails(movieDetails: MovieDetails) {
        details.value = movieDetails
    }

}