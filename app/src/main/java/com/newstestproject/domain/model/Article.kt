package com.newstestproject.domain.model

import org.joda.time.DateTime


data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: DateTime,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)