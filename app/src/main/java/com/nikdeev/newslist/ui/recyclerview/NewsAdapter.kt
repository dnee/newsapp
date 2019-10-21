package com.nikdeev.newslist.ui.recyclerview

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.data.source.network.State

private val ITEM_VIEW_TYPE_FOOTER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class NewsAdapter(val newsClickListener: NewsListener, val footerClickListener: FooterListener) :
    PagedListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_FOOTER -> FooterHolder.create(
                parent,
                footerClickListener
            )
            ITEM_VIEW_TYPE_ITEM -> NewsHolder.create(
                parent,
                newsClickListener
            )
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_VIEW_TYPE_ITEM -> (holder as NewsHolder).bind(getItem(position))
            ITEM_VIEW_TYPE_FOOTER -> (holder as FooterHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) ITEM_VIEW_TYPE_ITEM else ITEM_VIEW_TYPE_FOOTER
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return (state == State.LOADING || state == State.ERROR) && !(super.getItemCount() == 0 && state == State.LOADING)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News) =
                oldItem.newsId == newItem.newsId

            override fun areContentsTheSame(oldItem: News, newItem: News) =
                oldItem.urlToArticle == newItem.urlToArticle
        }
    }
}