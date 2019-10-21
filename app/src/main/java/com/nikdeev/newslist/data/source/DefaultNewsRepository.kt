package com.nikdeev.newslist.data.source

import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.data.Result
import com.nikdeev.newslist.data.Result.Error
import com.nikdeev.newslist.data.Result.Success
import com.nikdeev.newslist.di.AppModule.NewsLocalDataSource
import com.nikdeev.newslist.di.AppModule.NewsNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(
    @NewsNetworkDataSource private val newsRemoteDataSource: NewsDataSource,
    @NewsLocalDataSource private val newsLocalDataSource: NewsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {
    override suspend fun insertNewsPage(newsList: List<News>) {
        withContext(ioDispatcher) {
            try {
                newsLocalDataSource.insertNewsPage(newsList)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    override suspend fun getNewsPage(pageNum: Int): Result<List<News>> = withContext(ioDispatcher) {
        try {
            val networkNewsList = newsRemoteDataSource.getNewsPage(pageNum)
            when (networkNewsList) {
                is Success -> {
                    insertNewsPage(networkNewsList.data)
                    return@withContext networkNewsList
                }
                is Error -> {
                    return@withContext newsLocalDataSource.getNewsPage(pageNum)
                }
            }
        } catch (ex: Exception) {
            return@withContext Error(ex)
        }
    }

}