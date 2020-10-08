package com.example.searchmovies.ui.movielist.pagingadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovies.R
import kotlinx.android.synthetic.main.adapter_footer_loading.view.*

class LoadingFooterAdapter(private val retry : ()->Unit) : LoadStateAdapter<LoadingFooterViewHolder>(){
    override fun onBindViewHolder(holder: LoadingFooterViewHolder, loadState: LoadState) {
        val progress = holder.itemView.load_state_progress
        val btnRetry = holder.itemView.load_state_retry
        val txtErrorMessage = holder.itemView.load_state_errorMessage

        btnRetry.isVisible = loadState !is LoadState.Loading
        txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error){
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingFooterViewHolder {
        return LoadingFooterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_footer_loading, parent, false)
        )
    }
}


class LoadingFooterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)