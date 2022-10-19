package com.newstestproject.presentation.home

import androidx.compose.foundation.ScrollState
import com.newstestproject.core.util.UiText
import com.newstestproject.domain.model.Article

data class HomeState (
    val isLoading: Boolean = false,
    val data: List<Article> = emptyList(),
    val error: UiText? = null,
    val isSearchOpen: Boolean = false,
    val query: String = "",
    val categories: List<String> = emptyList(),
)