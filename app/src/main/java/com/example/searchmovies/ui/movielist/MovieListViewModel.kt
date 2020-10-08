package com.example.searchmovies.ui.movielist

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchmovies.base.BaseViewModel
import com.example.searchmovies.model.SearchItem
import com.example.searchmovies.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow


class MovieListViewModel(private val movieListRepository: MovieListRepository) : BaseViewModel() {

     var currentQueryValue: String? = null
     var currentSearchResult: Flow<PagingData<SearchItem>>? = null

    fun searchMovie(queryString: String): Flow<PagingData<SearchItem>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult = Pager(PagingConfig(pageSize = 1)) {
            MoviePagingSource(movieListRepository, queryString)
        }.flow.cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }



}