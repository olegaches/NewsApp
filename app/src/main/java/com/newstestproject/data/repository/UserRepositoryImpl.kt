package com.newstestproject.data.repository

import com.newstestproject.data.local.CategoryDao
import com.newstestproject.data.mappers.toCategoryEntity
import com.newstestproject.domain.model.Category
import com.newstestproject.domain.repository.UserRepository
import com.newstestproject.util.CategoryName
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
): UserRepository {

    override suspend fun getSelectedCategories(): List<CategoryName> {
        return categoryDao.getCategories().map { it.name }
    }

    override suspend fun addCategory(category: Category) {
        categoryDao.insertCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.name)
    }
}