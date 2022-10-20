package com.newstestproject.domain.use_case

import com.newstestproject.domain.repository.NewsRepository
import com.newstestproject.domain.repository.UserRepository
import com.newstestproject.util.CategoryName
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(): List<CategoryName> {
        return newsRepository.getAllCategories()
    }
}