package com.newstestproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.newstestproject.util.CategoryName

@Entity(tableName = "category_table")
data class CategoryEntity(
    val name: CategoryName,
    @PrimaryKey val id: Int? = null
)