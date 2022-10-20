package com.newstestproject.domain.repository

import com.newstestproject.domain.model.Category
import com.newstestproject.util.CategoryName

interface UserRepository {

    suspend fun getSelectedCategories(): List<CategoryName>

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}