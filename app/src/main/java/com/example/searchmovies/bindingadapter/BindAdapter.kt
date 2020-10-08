package com.example.searchmovies.bindingadapter

import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindAdapter {
    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImage(imageView: AppCompatImageView,url : String?){
        Glide.with(imageView.context).load(url).into(imageView)
    }

    @BindingAdapter("setHeightDividedBy")
    @JvmStatic
    fun setLayoutHeight(view: View, dividedBy: Double) {
        val layoutParams = view.layoutParams
        val displayMetrics: DisplayMetrics = view.context.resources.displayMetrics
        val height = displayMetrics.heightPixels / dividedBy
        layoutParams.height = height.toInt()
        view.layoutParams = layoutParams
    }

}