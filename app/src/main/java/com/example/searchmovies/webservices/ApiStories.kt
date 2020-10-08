package com.example.searchmovies.webservices

import com.example.searchmovies.model.MovieDetails
import com.example.searchmovies.model.MovieLists
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://www.omdbapi.com/"
const val API_KEY = "b9bd48a6"
const val TYPE = "movie"

interface ApiStories {
    @GET(".")
    suspend fun getMovieLists(
        @Query("s") movieName: String,
        @Query("apikey") api_key: String,
        @Query("page") page: Int,
        @Query("type") type: String
    ): MovieLists

    @GET(".")
    suspend fun getMovieDetails(
        @Query("i") id: String,
        @Query("apikey") api_key: String
    ): MovieDetails
}

