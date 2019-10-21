package com.nikdeev.newslist.data.source.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NetworkNews(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article>
) : Parcelable

@Parcelize
data class Article(
    val source: NewsSource,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    @Json(name = "url") val urlToArticle: String,
    val urlToImage: String? = null,
    @Json(name = "publishedAt") val publishDate: String? = null,
    val content: String? = null
) : Parcelable


@Parcelize
data class NewsSource(
    val id: String? = null,
    @Json(name = "name") val newsSource: String? = null
) : Parcelable