package com.newstestproject.data.repository

import com.google.gson.Gson
import com.newstestproject.R
import com.newstestproject.core.util.Resource
import com.newstestproject.core.util.UiText
import com.newstestproject.data.mappers.toNews
import com.newstestproject.data.remote.NewsApi
import com.newstestproject.data.remote.dto.ErrorDto
import com.newstestproject.util.NewsSortType
import com.newstestproject.domain.model.Article
import com.newstestproject.domain.repository.NewsRepository
import com.newstestproject.util.CategoryName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {

    override fun getAllNews(keyWord: String, sortBy: NewsSortType): Flow<Resource<List<Article>>> = flow {
        try {
            val data = api.getAllNews(
                keyWord = keyWord,
                sortBy = sortBy.toString(),
            )

            emit(Resource.Success(data = data.toNews().articles))
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
        categoryName: CategoryName?,
        keyWord: String?,
    ): Flow<Resource<List<Article>>> = flow {
        try {
            val data = api.getTopNews(
                keyWord = keyWord,
                categoryName?.name
            )

            emit(Resource.Success(data = data.toNews().articles))
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
}