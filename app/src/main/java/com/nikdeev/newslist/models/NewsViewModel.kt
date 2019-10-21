package com.nikdeev.newslist.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class NewsViewModel @Inject constructor() : ViewModel() {

    fun loadUrl(urlToArticle: String) {
        _urlToArticle.value = urlToArticle
    }

    private val _urlToArticle = MutableLiveData<String>()
    val urlToArticle: LiveData<String> = _urlToArticle
}