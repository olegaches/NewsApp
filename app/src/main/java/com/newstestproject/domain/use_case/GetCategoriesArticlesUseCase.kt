package com.newstestproject.domain.use_case

import com.newstestproject.core.util.Resource
import com.newstestproject.domain.model.Article
import com.newstestproject.domain.repository.NewsRepository
import com.newstestproject.util.CategoryName
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(categories: List<CategoryName>, keyWord: String? = null): Flow<Resource<List<Article>>> {
        return repository.getTopArticles(categories, keyWord)
    }
}