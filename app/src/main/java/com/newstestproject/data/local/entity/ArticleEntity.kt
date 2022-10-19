package com.newstestproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.newstestproject.domain.model.Source

@Entity(tableName = "article_table")
data class ArticleEntity(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?,
    @PrimaryKey val id: Int? = null
)