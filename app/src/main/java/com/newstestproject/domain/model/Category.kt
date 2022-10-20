package com.newstestproject.domain.model

import com.newstestproject.util.CategoryName

data class Category(
    val name: CategoryName,
    var selected: Boolean = false,
)