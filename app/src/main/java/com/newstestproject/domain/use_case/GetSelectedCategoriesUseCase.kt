package com.newstestproject.domain.use_case

import com.newstestproject.domain.repository.UserRepository
import com.newstestproject.util.CategoryName
import javax.inject.Inject

class GetSelectedCategoriesUseCase @Inject constructor(
private val repository: UserRepository
) {
    suspend operator fun invoke(): List<CategoryName> {
        return repository.getSelectedCategories()
    }
}