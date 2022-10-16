package com.newstestproject.data.mappers

import com.newstestproject.data.remote.dto.ArticleDto
import com.newstestproject.data.remote.dto.NewsDto
import com.newstestproject.data.remote.dto.SourceDto
import com.newstestproject.domain.model.Article
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