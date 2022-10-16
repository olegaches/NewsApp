package com.newstestproject.data.mappers

import com.newstestproject.data.local.entity.CategoryEntity
import com.newstestproject.domain.model.Category

fun CategoryEntity.toCategoryName(): String {
    return this.name
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        name = name
    )
}