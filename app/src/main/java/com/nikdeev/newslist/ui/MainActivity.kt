package com.nikdeev.newslist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikdeev.newslist.R
import com.nikdeev.newslist.models.ListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var listViewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
