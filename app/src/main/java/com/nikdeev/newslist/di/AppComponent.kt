package com.nikdeev.newslist.di

import android.content.Context
import com.nikdeev.newslist.NewsApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ListModule::class,
        NewsModule::class]
)
interface ApplicationComponent : AndroidInjector<NewsApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}