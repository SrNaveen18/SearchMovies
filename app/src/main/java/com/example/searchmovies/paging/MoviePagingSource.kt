package com.example.searchmovies.paging

import androidx.paging.PagingSource
import com.example.searchmovies.model.SearchItem
import com.example.searchmovies.ui.movielist.MovieListRepository

class MoviePagingSource(private val repository: MovieListRepository,private val movieName : String) :
    PagingSource<Int, SearchItem>() {
    private val initialPageIndex: Int = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        return try {
            val position = params.key ?: initialPageIndex
            val movieLists = repository.getAlbumList(movieName = movieName, page = position)
            LoadResult.Page(
                data = movieLists.search,
                prevKey = if (position == initialPageIndex) null else position - 1,
                nextKey = if (movieLists.search.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
