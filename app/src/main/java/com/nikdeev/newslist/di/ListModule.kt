package com.nikdeev.newslist.di

import androidx.lifecycle.ViewModel
import com.nikdeev.newslist.models.ListViewModel
import com.nikdeev.newslist.ui.ListFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ListModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun listFragment(): ListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindViewModel(viewmodel: ListViewModel): ViewModel
}