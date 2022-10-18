package com.newstestproject.data.remote

import com.newstestproject.data.remote.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("q") keyWord: String?,
        @Query("category") category: String?,
        @Query("country") country: String = "ru",
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsDto

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q") keyWord: String,
        @Query("sortBy") sortBy: String,
        @Query("language") language: String = "ru",
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsDto

    companion object {
        const val BASE_URL = "https://newsapi.org/"
        const val API_KEY = "cd8a30da57c048afa64f129c38f339d3"
    }
}