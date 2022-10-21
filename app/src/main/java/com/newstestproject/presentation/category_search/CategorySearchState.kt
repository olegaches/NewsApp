package com.newstestproject.presentation.category_search

import com.newstestproject.domain.model.Category
import com.newstestproject.util.CategoryName

data class CategorySearchState(
    val categories: List<Category> = emptyList(),
    val query: String = "",
)