package com.example.mvvmex.ext

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mvvmex.entity.MovieResult

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindImage")
    fun ImageView.bindImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("bindTitle")
    fun TextView.bindTitle(movie: MovieResult.Item) {
        this.text = Html.fromHtml("${movie.title} (${movie.pubDate})")
    }

}
