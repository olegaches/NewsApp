package com.newstestproject.data.repository

import com.google.gson.Gson
import com.newstestproject.R
import com.newstestproject.core.util.Resource
import com.newstestproject.core.util.UiText
import com.newstestproject.data.local.ArticleDao
import com.newstestproject.data.mappers.toArticle
import com.newstestproject.data.mappers.toArticleEntity
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
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val articleDao: ArticleDao,
): NewsRepository {

    override fun getAllNews(keyWord: String, sortBy: NewsSortType): Flow<Resource<List<Article>>> = flow {
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
            val cacheArticles = articleDao.getArticles().map { it.toArticle() }
            if(e.localizedMessage.isNullOrEmpty()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_exception), cacheArticles))
                return@flow
            }
            val errorBody = e.response()?.errorBody()
            if (errorBody == null) {
                emit(Resource.Error(UiText.DynamicString(e.localizedMessage!!), cacheArticles))
            }
            else {
                val errorMessage = Gson().fromJson(errorBody.charStream(), ErrorDto::class.java).message
                emit(Resource.Error(UiText.DynamicString(errorMessage), cacheArticles))
            }
        }
        catch(e: IOException) {
            val cacheArticles = articleDao.getArticles().map { it.toArticle() }
            emit(Resource.Error(UiText.StringResource(R.string.io_exception), cacheArticles))
        }
    }

    override fun getTopArticles(
        categories: List<CategoryName>,
        keyWord: String?,
    ): Flow<Resource<List<Article>>> = flow {
        val remoteLists = mutableListOf<List<ArticleDto>>()
        var errorMessage: UiText? = null
        coroutineScope {
            val cacheArticles = async(Dispatchers.IO) { articleDao.getArticles().map { it.toArticle() }.sortedByDescending { it.publishedAt } }
            try {
                for(category in categories) {
                    launch(Dispatchers.IO) {
                        val remoteArticles = api.getTopNews(
                            keyWord = keyWord,
                            category = category.name
                        ).articles
                        remoteLists.add(remoteArticles)
                    }
                }

                articleDao.deleteAllArticles()
                val articles = remoteLists.flatMap {
                    it.sortedByDescending { article ->
                        article.publishedAt
                    }
                }

                articleDao.insertArticles(articles.map { it.toArticleEntity() })
                emit(Resource.Success(data = articles.map { it.toArticle() }))
            }
            catch(e: HttpException) {
                errorMessage = if(e.localizedMessage.isNullOrEmpty()) {
                    UiText.StringResource(R.string.unknown_exception)
                }
                else {
                    val errorBody = e.response()?.errorBody()
                    if(errorBody == null) {
                        UiText.DynamicString(e.localizedMessage!!)
                    } else {
                        UiText.DynamicString(Gson().fromJson(errorBody.charStream(), ErrorDto::class.java).message)
                    }
                }
            }
            catch(e: IOException) {
                errorMessage = UiText.StringResource(R.string.io_exception)
            }
            finally {
                errorMessage?.let {
                    emit(Resource.Error(errorMessage!!, cacheArticles.await()))
                    this.cancel()
                }
            }
        }
    }

    override fun getAllCategories(): List<CategoryName> {
        return CategoryName.values().filter { it != CategoryName.general }
    }
}