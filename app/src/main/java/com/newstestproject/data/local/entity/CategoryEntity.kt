package com.newstestproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryEntity(
    val name: String,
    @PrimaryKey val id: Int? = null
)