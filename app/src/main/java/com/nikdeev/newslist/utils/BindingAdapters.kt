package com.nikdeev.newslist.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nikdeev.newslist.R
import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.ui.recyclerview.NewsAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .override(64, 64)
            .into(imgView)
    }
}

@BindingAdapter("listNews")
fun bindRecyclerView(recyclerView: RecyclerView, data: PagedList<News>?) {
    if (data != null) {
        val adapter = recyclerView.adapter as NewsAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("shortNewsDate")
fun bindTextView(textView: TextView, date: String) {
    textView.text = date.substring(0..9)
}