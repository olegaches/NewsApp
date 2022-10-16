package com.newstestproject.presentation.categories

import com.newstestproject.domain.model.Category

data class CategoriesState(
    val isLoading: Boolean? = false,
    val categories: List<Category> = emptyList(),
)