package com.example.searchmovies.ui.moviedetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.searchmovies.BR
import com.example.searchmovies.R
import com.example.searchmovies.base.BaseFragment
import com.example.searchmovies.databinding.FragmentMovieDetailsBinding
import com.example.searchmovies.model.ApiResponse
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.ext.android.inject

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    private val movieDetailsViewModel: MovieDetailsViewModel by inject()
    private val navArgs: MovieDetailsFragmentArgs by navArgs()

    override fun getViewModel(): MovieDetailsViewModel? = movieDetailsViewModel

    override fun getBindingVariable(): Int = BR.movieDetailsVM

    override fun getContentView(): Int = R.layout.fragment_movie_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.setMovieID(navArgs.id)
        movieDetailsViewModel.requestMovieDetails().observe(viewLifecycleOwner, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    movieDetailsViewModel.setMovieDetails(apiResponse.response)
                    showDetails()
                }
                is ApiResponse.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        apiResponse.exception.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ApiResponse.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
                ApiResponse.COMPLETED -> {
                    progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun showDetails() {
        group.visibility = View.VISIBLE
    }

}