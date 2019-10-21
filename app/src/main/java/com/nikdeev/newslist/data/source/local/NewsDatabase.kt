package com.nikdeev.newslist.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikdeev.newslist.data.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}