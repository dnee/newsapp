package com.nikdeev.newslist.data.source.local

import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.data.Result
import com.nikdeev.newslist.data.Result.Error
import com.nikdeev.newslist.data.Result.Success
import com.nikdeev.newslist.data.source.NewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsLocalDataSource internal constructor(
    private val newsDao: NewsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsDataSource {
    override suspend fun getNewsPage(pageNum: Int): Result<List<News>> = withContext(ioDispatcher) {
        try {
            val newsList = newsDao.getPage(pageNum)
            return@withContext if (newsList.isNotEmpty()) Success(newsList) else Error(Exception("Empty news page"))
        } catch (ex: Exception) {
            return@withContext Error(ex)
        }
    }

    override suspend fun insertNewsPage(newsList: List<News>) = withContext(ioDispatcher) {
        if (newsList.isNotEmpty())
            newsDao.insertAll(newsList)
    }
}