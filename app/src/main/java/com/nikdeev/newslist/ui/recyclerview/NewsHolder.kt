package com.nikdeev.newslist.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.databinding.ListItemNewsBinding

class NewsHolder private constructor(
    val binding: ListItemNewsBinding,
    val clickListener: NewsListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: News?) {
        if (item != null) {
            binding.news = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup, clickListener: NewsListener): NewsHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemNewsBinding.inflate(layoutInflater, parent, false)
            return NewsHolder(binding, clickListener)
        }
    }
}

class NewsListener(val clickListener: (urlToArticle: String) -> Unit) {
    fun onClick(news: News) = clickListener(news.urlToArticle)
}