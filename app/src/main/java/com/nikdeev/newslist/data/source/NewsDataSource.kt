package com.nikdeev.newslist.data.source

import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.data.Result

interface NewsDataSource {
    suspend fun insertNewsPage(newsList: List<News>)

    suspend fun getNewsPage(pageNum: Int): Result<List<News>>
}