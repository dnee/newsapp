package com.nikdeev.newslist.di

import androidx.lifecycle.ViewModel
import com.nikdeev.newslist.models.NewsViewModel
import com.nikdeev.newslist.ui.NewsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class NewsModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun newsFragment(): NewsFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindViewModel(viewmodel: NewsViewModel): ViewModel
}