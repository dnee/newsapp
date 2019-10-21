package com.nikdeev.newslist.di

import android.content.Context
import androidx.room.Room
import com.nikdeev.newslist.data.source.DefaultNewsRepository
import com.nikdeev.newslist.data.source.NewsDataSource
import com.nikdeev.newslist.data.source.NewsRepository
import com.nikdeev.newslist.data.source.local.NewsDatabase
import com.nikdeev.newslist.data.source.local.NewsLocalDataSource
import com.nikdeev.newslist.data.source.network.NewsApi
import com.nikdeev.newslist.data.source.network.NewsApiService
import com.nikdeev.newslist.data.source.network.NewsNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module(includes = [ApplicationModuleBinds::class])
object AppModule {
    @Qualifier
    @Retention(RUNTIME)
    annotation class NewsNetworkDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class NewsLocalDataSource

    @JvmStatic
    @Singleton
    @NewsNetworkDataSource
    @Provides
    fun provideNewsNetworkDataSource(
        newsApiService: NewsApiService,
        ioDispatcher: CoroutineDispatcher
    ): NewsDataSource {
        return NewsNetworkDataSource(newsApiService, ioDispatcher)
    }

    @JvmStatic
    @Singleton
    @NewsLocalDataSource
    @Provides
    fun provideNewsLocalDataSource(
        database: NewsDatabase,
        ioDispatcher: CoroutineDispatcher
    ): NewsDataSource {
        return NewsLocalDataSource(
            database.newsDao(), ioDispatcher
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "NewsDatabase.db"
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @JvmStatic
    @Singleton
    @Provides
    fun provideNewsApiService(): NewsApiService = NewsApi.retrofitService
}

@Module
abstract class ApplicationModuleBinds {
    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultNewsRepository): NewsRepository
}