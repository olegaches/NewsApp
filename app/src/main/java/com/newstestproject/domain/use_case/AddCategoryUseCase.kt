package com.newstestproject.domain.use_case

import com.newstestproject.domain.model.Category
import com.newstestproject.domain.repository.UserRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.addCategory(category)
    }
}