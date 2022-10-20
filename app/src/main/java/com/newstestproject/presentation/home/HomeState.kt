package com.newstestproject.presentation.home

import com.newstestproject.core.util.UiText
import com.newstestproject.domain.model.Article
import com.newstestproject.util.CategoryName

data class HomeState (
    val isLoading: Boolean = false,
    val data: List<Article> = emptyList(),
    val error: UiText? = null,
    val isSearchOpen: Boolean = false,
    val selectedFilter: CategoryName = CategoryName.general,
    val query: String = "",
    val categories: List<CategoryName> = emptyList(),
)