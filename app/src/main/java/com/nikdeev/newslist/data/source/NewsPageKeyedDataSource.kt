package com.nikdeev.newslist.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.nikdeev.newslist.data.News
import com.nikdeev.newslist.data.Result.Error
import com.nikdeev.newslist.data.Result.Success
import com.nikdeev.newslist.data.source.network.State
import com.nikdeev.newslist.models.ListViewModel.Companion.LAST_PAGE
import kotlinx.coroutines.*
import timber.log.Timber

class NewsPageKeyedDataSource(
    private val repository: NewsRepository,
    private val viewModelScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : PageKeyedDataSource<Int, News>() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val _errMsg = MutableLiveData<String>()
    val errMsg: LiveData<String>
        get() = _errMsg

    private var loadInitialCallback: LoadInitialCallback<Int, News>? = null
    private var loadInitialParams: LoadInitialParams<Int>? = null

    private var loadCallback: LoadCallback<Int, News>? = null
    private var loadParams: LoadParams<Int>? = null

    private var initialSuccess = false

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        if (state.value != State.LOADING) {
            updateState(State.LOADING)
            viewModelScope.launch {
                withContext(ioDispatcher) {
                    val newsResult = repository.getNewsPage(params.key)
                    when (newsResult) {
                        is Success -> {
                            if (params.key != LAST_PAGE) callback.onResult(
                                newsResult.data,
                                params.key + 1
                            ) else callback.onResult(newsResult.data, null)
                            updateState(State.DONE)
                        }
                        is Error -> {
                            loadParams = params
                            loadCallback = callback
                            Timber.e(newsResult.exception)
                            updateState(State.ERROR)
                            updateErrorMsg("Не удалось получить данные от сервера. Проверьте соединение")
                        }
                    }
                }
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        if (state.value != State.LOADING) {
            updateState(State.LOADING)
            viewModelScope.launch {
                withContext(ioDispatcher) {
                    val newsResult = repository.getNewsPage(1)
                    when (newsResult) {
                        is Success -> {
                            callback.onResult(newsResult.data, null, 2)
                            initialSuccess = true
                            updateState(State.DONE)
                        }
                        is Error -> {
                            loadInitialParams = params
                            loadInitialCallback = callback
                            Timber.e(newsResult.exception)
                            updateState(State.ERROR)
                            updateErrorMsg("Не удалось получить данные от сервера. Проверьте соединение")
                        }
                    }
                }
            }
        }
    }

    fun retry() {
        if (!initialSuccess && loadInitialParams != null && loadInitialCallback != null) {
            val params: LoadInitialParams<Int> = loadInitialParams!!
            val callback: LoadInitialCallback<Int, News> = loadInitialCallback!!
            loadInitial(params, callback)
        } else if (loadParams != null && loadCallback != null) {
            val params: LoadParams<Int> = loadParams!!
            val callback: LoadCallback<Int, News> = loadCallback!!
            loadAfter(params, callback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun updateErrorMsg(errMsg: String) {
        _errMsg.postValue(errMsg)
    }

    private fun updateState(state: State) {
        _state.postValue(state)
    }

}