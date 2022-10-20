package com.newstestproject.data.repository

import com.google.gson.Gson
import com.newstestproject.R
import com.newstestproject.core.util.Resource
import com.newstestproject.core.util.UiText
import com.newstestproject.data.local.ArticleDao
import com.newstestproject.data.mappers.toArticle
import com.newstestproject.data.mappers.toArticleEntity
import com.newstestproject.data.mappers.toNews
import com.newstestproject.data.remote.NewsApi
import com.newstestproject.data.remote.dto.ArticleDto
import com.newstestproject.data.remote.dto.ErrorDto
import com.newstestproject.util.NewsSortType
import com.newstestproject.domain.model.Article
import com.newstestproject.domain.repository.NewsRepository
import com.newstestproject.util.CategoryName
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val articleDao: ArticleDao,
): NewsRepository {

    override fun getAllNews(keyWord: String, sortBy: NewsSortType): Flow<Resource<List<Article>>> = flow {
        val articles = articleDao.getArticles().map { it.toArticle() }
        emit(Resource.Loading(articles))

        try {
            val remoteArticles = api.getAllNews(
                keyWord = keyWord,
                sortBy = sortBy.toString(),
            ).articles

            articleDao.deleteAllArticles()
            articleDao.insertArticles(remoteArticles.map { it.toArticleEntity() })
            val newArticles = articleDao.getArticles().map { it.toArticle() }
            emit(Resource.Success(data = newArticles))
        }
        catch(e: HttpException) {
            if(e.localizedMessage.isNullOrEmpty()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_exception)))
                return@flow
            }
            val errorBody = e.response()?.errorBody()
            if (errorBody == null) {
                emit(Resource.Error(UiText.DynamicString(e.localizedMessage!!)))
            }
            else {
                val errorMessage = Gson().fromJson(errorBody.charStream(), ErrorDto::class.java).message
                emit(Resource.Error(UiText.DynamicString(errorMessage)))
            }
        }
        catch(e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.io_exception)))
        }
    }

    override fun getTopArticles(
        categories: List<CategoryName>,
        keyWord: String?,
    ): Flow<Resource<List<Article>>> = flow {
        try {
            val deferredLists = mutableListOf<Deferred<List<ArticleDto>>>()

            coroutineScope {
                for(category in categories) {
                    val deferredItem = async {
                        val remoteArticles = api.getTopNews(
                            keyWord = keyWord,
                            category = category.name
                        ).articles
                        remoteArticles
                    }
                    deferredLists.add(deferredItem)
                }
            }

            articleDao.deleteAllArticles()

            val remoteArticles = mutableListOf<ArticleDto>()
            for(list in deferredLists.awaitAll()) {
                remoteArticles.addAll(list)
            }

            remoteArticles.sortByDescending { it.publishedAt }
            articleDao.insertArticles(remoteArticles.map { it.toArticleEntity() })

            emit(Resource.Success(data = remoteArticles.map { it.toArticle() }))
        }
        catch(e: HttpException) {
            val cacheArticles = articleDao.getArticles().map { it.toArticle() }.sortedByDescending { it.publishedAt }
            if(e.localizedMessage.isNullOrEmpty()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_exception), cacheArticles))
            }
            val errorBody = e.response()?.errorBody()
            if(errorBody == null) {
                emit(Resource.Error(UiText.DynamicString(e.localizedMessage!!), cacheArticles))
            } else {
                val errorMessage = Gson().fromJson(errorBody.charStream(), ErrorDto::class.java).message
                emit(Resource.Error(UiText.DynamicString(errorMessage), cacheArticles))
            }
        }
        catch(e: IOException) {
            val cacheArticles = articleDao.getArticles().map { it.toArticle() }.sortedByDescending { it.publishedAt }
            emit(Resource.Error(UiText.StringResource(R.string.io_exception),cacheArticles ))
        }
    }

    override fun getAllCategories(): List<CategoryName> {
        return CategoryName.values().filter { it != CategoryName.general }
    }
}