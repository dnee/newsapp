package com.nikdeev.newslist.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikdeev.newslist.data.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<News>)

    @Query("SELECT * FROM news_table WHERE news_page = :pageNum ORDER BY newsId ASC")
    suspend fun getPage(pageNum: Int): List<News>
}