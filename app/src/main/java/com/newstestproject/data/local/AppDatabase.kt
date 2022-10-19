package com.newstestproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newstestproject.data.local.entity.ArticleEntity
import com.newstestproject.data.local.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class, ArticleEntity::class],
    version = 2
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract val categoryDao: CategoryDao
    abstract val articleDao: ArticleDao

    companion object {
        const val name = "app_dp"
    }
}