package com.newstestproject.domain.use_case

import com.newstestproject.domain.repository.UserRepository
import javax.inject.Inject

class GetSelectedCategoriesUseCase @Inject constructor(
private val repository: UserRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getSelectedCategories()
    }
}