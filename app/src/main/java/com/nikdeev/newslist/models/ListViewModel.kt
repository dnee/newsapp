package com.nikdeev.newslist.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.data.source.NewsPageKeyedDataSource
import com.nikdeev.newslist.data.source.NewsPageKeyedDataSourceFactory
import com.nikdeev.newslist.data.source.NewsRepository
import com.nikdeev.newslist.data.source.network.State
import javax.inject.Inject

class ListViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val newsDataSourceFactory =
        NewsPageKeyedDataSourceFactory(newsRepository, viewModelScope)
    val news: LiveData<PagedList<News>> =
        LivePagedListBuilder<Int, News>(newsDataSourceFactory, getPagedListConfig()).build()

    private fun getPagedListConfig() = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setInitialLoadSizeHint(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_COUNT)
        .setEnablePlaceholders(false)
        .build()

    fun onRetryButtonClicked() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun getErrorMsg(): LiveData<String> = Transformations.switchMap<NewsPageKeyedDataSource,
            String>(newsDataSourceFactory.newsDataSourceLiveData, NewsPageKeyedDataSource::errMsg)

    fun getState(): LiveData<State> = Transformations.switchMap<NewsPageKeyedDataSource,
            State>(newsDataSourceFactory.newsDataSourceLiveData, NewsPageKeyedDataSource::state)

    companion object {
        const val PAGE_SIZE: Int = 20
        const val LAST_PAGE: Int = 5
        const val PREFETCH_COUNT: Int = 5
    }
}


