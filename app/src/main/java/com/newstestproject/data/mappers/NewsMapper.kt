package com.newstestproject.data.mappers

import com.newstestproject.data.local.entity.ArticleEntity
import com.newstestproject.data.local.entity.CategoryEntity
import com.newstestproject.data.remote.dto.ArticleDto
import com.newstestproject.data.remote.dto.NewsDto
import com.newstestproject.data.remote.dto.SourceDto
import com.newstestproject.domain.model.Article
import com.newstestproject.domain.model.Category
import com.newstestproject.domain.model.News
import com.newstestproject.domain.model.Source
import org.joda.time.DateTime

fun NewsDto.toNews(): News {
    return News(
        articles = articles.map { it.toArticle() },
        status = status,
        totalResults = totalResults
    )
}

fun ArticleDto.toArticle(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = DateTime(publishedAt),
        source = source.toSource(),
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}

fun SourceDto.toSource(): Source {
    return Source(
        id = id,
        name = name,
    )
}

fun CategoryEntity.toCategoryName(): String {
    return this.name
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        name = name
    )
}

fun ArticleDto.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.toSource(),
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = DateTime(publishedAt),
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}