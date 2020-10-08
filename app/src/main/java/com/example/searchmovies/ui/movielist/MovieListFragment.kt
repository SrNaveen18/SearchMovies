package com.example.searchmovies.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchmovies.BR
import com.example.searchmovies.R
import com.example.searchmovies.base.BaseFragment
import com.example.searchmovies.databinding.FragmentMovieListBinding
import com.example.searchmovies.ui.movielist.pagingadapter.LoadingFooterAdapter
import com.example.searchmovies.ui.movielist.pagingadapter.PagingAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>(),
    View.OnClickListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private val movieListViewModel: MovieListViewModel by inject()
    private var pagingAdapter: PagingAdapter? = null

    override fun getViewModel(): MovieListViewModel? = movieListViewModel

    override fun getBindingVariable(): Int = BR.movieListVM

    override fun getContentView(): Int = R.layout.fragment_movie_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.setOnQueryTextListener(this)
        btnSearch.setOnClickListener(this)
        initAdapter()
        checkCachedValues()
    }

    private fun initAdapter() {
        pagingAdapter = PagingAdapter(listener = this)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = pagingAdapter
        }

        recyclerView.apply {
            adapter = pagingAdapter?.withLoadStateFooter(
                footer = LoadingFooterAdapter { pagingAdapter?.retry() }
            )
        }
    }

    private var searchJob: Job? = null

    private fun search(query: String) {
        showProgress()
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            movieListViewModel.searchMovie(query).collectLatest {
                pagingAdapter?.submitData(it)
            }
        }
        hideProgress()
    }

    private fun checkCachedValues() {
        movieListViewModel.currentSearchResult?.let {
            movieListViewModel.currentQueryValue?.let {
                search(it)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cardView -> {
                val id = v.getTag(R.id.cardView) as String
                navigateToDetailsScreen(id)
            }
            R.id.btnSearch -> {
                search(searchView.query.toString())
                searchView.clearFocus()
            }
        }
    }

    private fun navigateToDetailsScreen(id: String) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query.toString())
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}