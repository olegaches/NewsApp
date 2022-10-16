package com.newstestproject.domain.repository

import com.newstestproject.domain.model.Category

interface UserRepository {

    suspend fun getSelectedCategories(): List<String>

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}