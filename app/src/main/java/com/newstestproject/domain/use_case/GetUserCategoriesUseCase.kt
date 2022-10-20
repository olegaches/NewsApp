package com.newstestproject.domain.use_case

import com.newstestproject.domain.repository.UserRepository
import com.newstestproject.util.CategoryName
import javax.inject.Inject

class GetUserCategoriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): List<CategoryName> {
        return userRepository.getSelectedCategories()
    }
}