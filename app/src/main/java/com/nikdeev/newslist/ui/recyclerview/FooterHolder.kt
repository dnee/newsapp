package com.nikdeev.newslist.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikdeev.newslist.databinding.FooterBinding

class FooterHolder private constructor(
    val binding: FooterBinding,
    val clickListener: FooterListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup, clickListener: FooterListener): FooterHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FooterBinding.inflate(layoutInflater, parent, false)
            return FooterHolder(binding, clickListener)
        }
    }
}

class FooterListener(val clickListener: () -> Unit) {
    fun onClick() = clickListener()
}