package com.newstestproject.domain.use_case

import com.newstestproject.domain.model.Category
import com.newstestproject.domain.repository.NewsRepository
import com.newstestproject.domain.repository.UserRepository
import com.newstestproject.util.CategoryName
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): List<Category> {
        val selectedCategories = userRepository.getSelectedCategories()
        val categoriesList = mutableListOf<Category>()
        val categoriesNames = newsRepository.getAllCategories()
        for(categoryName in categoriesNames) {
            if(selectedCategories.contains(categoryName)) {
                categoriesList.add(Category(categoryName, true))
            }
            else {
                categoriesList.add(Category(categoryName))
            }
        }

        return categoriesList
    }
}