package com.newstestproject.data.remote.dto

data class NewsDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)