package com.newstestproject.presentation.home

import com.newstestproject.domain.model.Article

data class HomeState (
    val isLoading: Boolean = false,
    val data: List<Article> = emptyList(),
)