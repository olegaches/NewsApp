package com.newstestproject.data.local

import androidx.room.*
import com.newstestproject.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM article_table WHERE id=:id")
    suspend fun deleteArticle(id: Int)

    @Query("DELETE FROM article_table")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM article_table ORDER BY id DESC")
    suspend fun getArticles(): List<ArticleEntity>

}