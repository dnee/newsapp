package com.nikdeev.newslist.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nikdeev.newslist.data.News
import kotlinx.coroutines.CoroutineScope

class NewsPageKeyedDataSourceFactory(
    private val repository: NewsRepository,
    private val viewModelScope: CoroutineScope
) : DataSource.Factory<Int, News>() {

    private val _newsDataSourceLiveData = MutableLiveData<NewsPageKeyedDataSource>()
    val newsDataSourceLiveData: LiveData<NewsPageKeyedDataSource>
        get() = _newsDataSourceLiveData

    override fun create(): DataSource<Int, News> {
        val newsDataSource =
            NewsPageKeyedDataSource(
                repository,
                viewModelScope
            )
        _newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}