package com.newstestproject.data.local

import androidx.room.*
import com.newstestproject.data.local.entity.CategoryEntity
import com.newstestproject.util.CategoryName

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("DELETE FROM category_table WHERE name=:name")
    suspend fun deleteCategory(name: CategoryName)

    @Query("SELECT * FROM category_table ORDER BY id DESC")
    suspend fun getCategories(): List<CategoryEntity>

}