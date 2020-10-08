package com.example.searchmovies.ui.moviedetails

import com.example.searchmovies.model.ApiResponse
import com.example.searchmovies.model.MovieDetails
import com.example.searchmovies.webservices.API_KEY
import com.example.searchmovies.webservices.ApiStories

class MovieDetailsRepository(private val apiStories: ApiStories) {

    suspend fun getMovieDetails(movieId: String): ApiResponse<MovieDetails> {
        return try {
            val callApi = apiStories.getMovieDetails(api_key = API_KEY, id = movieId)
            ApiResponse.Success(callApi)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }
}