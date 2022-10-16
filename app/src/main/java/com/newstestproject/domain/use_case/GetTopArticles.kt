package com.newstestproject.domain.use_case

import com.newstestproject.core.util.Resource
import com.newstestproject.domain.model.Article
import com.newstestproject.domain.repository.NewsRepository
import com.newstestproject.util.CategoryName
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopArticles @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(keyWord: String, categoryName: CategoryName): Flow<Resource<List<Article>>> {
        return repository.getTopArticles(keyWord, categoryName)
    }
}