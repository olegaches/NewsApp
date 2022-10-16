package com.newstestproject.domain.use_case

import com.newstestproject.domain.model.Category
import com.newstestproject.domain.repository.UserRepository
import com.newstestproject.util.CategoryName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<Category>> = flow {
        val selectedCategories = repository.getSelectedCategories()
        val categoriesList = mutableListOf<Category>()
        val categoriesNames = CategoryName.values()
        for(categoryName in categoriesNames) {
            if(selectedCategories.contains(categoryName.name)) {
                categoriesList.add(Category(categoryName.name, true))
            }
            else {
                categoriesList.add(Category(categoryName.name))
            }
        }

        emit(categoriesList)
    }
}