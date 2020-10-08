package com.example.searchmovies.ui.movielist

import com.example.searchmovies.model.MovieLists
import com.example.searchmovies.webservices.API_KEY
import com.example.searchmovies.webservices.ApiStories
import com.example.searchmovies.webservices.TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieListRepository(private val apiStories: ApiStories) {
    suspend fun getAlbumList(movieName: String, page: Int): MovieLists {
        return withContext(Dispatchers.IO) {
            apiStories.getMovieLists(
                movieName = movieName,
                api_key = API_KEY,
                page = page,
                type = TYPE
            )
        }
    }
}