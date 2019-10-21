package com.nikdeev.newslist.data.source.network

import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.data.Result
import com.nikdeev.newslist.data.Result.Error
import com.nikdeev.newslist.data.Result.Success
import com.nikdeev.newslist.data.source.NewsDataSource
import com.nikdeev.newslist.models.ListViewModel.Companion.PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsNetworkDataSource(
    private val newsApiService: NewsApiService, private val ioDispatcher: CoroutineDispatcher =
        Dispatchers.IO
) : NewsDataSource {

    override suspend fun getNewsPage(pageNum: Int): Result<List<News>> = withContext(ioDispatcher) {
        try {
            val news = mapToNewsList(newsApiService.getProperties(pageNum), pageNum)
            if (news.isNotEmpty()) return@withContext Success(news) else return@withContext Error(
                Exception("[NetworkDataSource] Empty news list")
            )
        } catch (ex: Exception) {
            return@withContext Error(ex)
        }
    }

    override suspend fun insertNewsPage(newsList: List<News>) {
        //TODO("not used") //To change body of created functions use File | Settings | File Templates.
    }

    private fun mapToNewsList(newtorkNews: NetworkNews, pageNumber: Int): List<News> {
        val news = newtorkNews.articles.map {
            News(
                0,
                it.urlToArticle,
                it.urlToImage,
                it.source.newsSource,
                pageNumber,
                it.title,
                it.description,
                it.publishDate
            )
        }
        for (i in news.indices) {
            news[i].newsId = i + 1 + PAGE_SIZE * (pageNumber - 1)
        }
        return news
    }

}