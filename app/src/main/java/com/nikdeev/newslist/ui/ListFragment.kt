package com.nikdeev.newslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikdeev.newslist.R
import com.nikdeev.newslist.databinding.FragmentListBinding
import com.nikdeev.newslist.models.ListViewModel
import com.nikdeev.newslist.ui.recyclerview.FooterListener
import com.nikdeev.newslist.ui.recyclerview.NewsAdapter
import com.nikdeev.newslist.ui.recyclerview.NewsListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ListFragment : DaggerFragment() {
    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ListViewModel> { viewModelFactory }

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsAdapter = NewsAdapter(
            NewsListener { urlToArticle ->
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToNewsFragment(
                        urlToArticle
                    )
                )
            },
            FooterListener { viewModel.onRetryButtonClicked() })

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.listViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.newsList.adapter = newsAdapter
        binding.newsList.layoutManager = LinearLayoutManager(this.context)
        binding.executePendingBindings()

        viewModel.getErrorMsg().observe(this, Observer {
            Toast.makeText(this.activity, it, Toast.LENGTH_LONG).show()
        })

        viewModel.getState().observe(this, Observer {
            newsAdapter.setState(it)
        })

        return binding.root
    }
}
