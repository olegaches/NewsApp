package com.newstestproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newstestproject.data.local.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1
)
abstract class AppDataBase: RoomDatabase() {

    abstract val categoryDao: CategoryDao
}