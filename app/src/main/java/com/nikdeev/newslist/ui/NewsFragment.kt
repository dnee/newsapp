package com.nikdeev.newslist.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nikdeev.newslist.R
import com.nikdeev.newslist.databinding.FragmentNewsBinding
import com.nikdeev.newslist.models.NewsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsFragment : DaggerFragment() {
    private lateinit var binding: FragmentNewsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<NewsViewModel> { viewModelFactory }

    private val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.executePendingBindings()

        viewModel.urlToArticle.observe(this, Observer {
            binding.webView.loadUrl(it)
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadUrl(args.urlToArticle)
    }
}
