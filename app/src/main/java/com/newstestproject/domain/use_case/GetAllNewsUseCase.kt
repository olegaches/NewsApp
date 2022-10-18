package com.newstestproject.domain.use_case

import com.newstestproject.core.util.Resource
import com.newstestproject.util.NewsSortType
import com.newstestproject.domain.model.Article
import com.newstestproject.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(keyWord: String, sortBy: NewsSortType): Flow<Resource<List<Article>>> {
        return repository.getAllNews(keyWord, sortBy)
    }
}