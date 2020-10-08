package com.example.searchmovies.ui.movielist.pagingadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.searchmovies.R
import com.example.searchmovies.databinding.AdapterPagingBinding
import com.example.searchmovies.model.SearchItem


class PagingAdapter(val listener: View.OnClickListener
) : PagingDataAdapter<SearchItem, PagingViewHolder>(MovieComparator) {


    object MovieComparator : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            // Id is unique.
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        holder.onBind(getItem(position), listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_paging,
                parent,
                false
            )
        )
    }

}

class PagingViewHolder(private val adapterPagingBinding: AdapterPagingBinding) :
    RecyclerView.ViewHolder(adapterPagingBinding.root) {

    fun onBind(
        searchItem: SearchItem?,
        listener: View.OnClickListener
    ) {
        adapterPagingBinding.searchItem = searchItem
        adapterPagingBinding.cardView.setTag(R.id.cardView,searchItem?.imdbID)
        adapterPagingBinding.cardView.setOnClickListener(listener)
    }
}
