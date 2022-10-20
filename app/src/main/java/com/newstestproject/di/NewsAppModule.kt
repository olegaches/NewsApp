package com.newstestproject.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.newstestproject.data.local.AppDatabase
import com.newstestproject.data.local.ArticleDao
import com.newstestproject.data.local.CategoryDao
import com.newstestproject.data.local.Converters
import com.newstestproject.data.remote.NewsApi
import com.newstestproject.data.util.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsAppModule {

    @Provides
    @Singleton
    fun provideInterviewApplicationApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(
        db: AppDatabase,
    ): CategoryDao {
        return db.categoryDao
    }

    @Provides
    @Singleton
    fun provideArticleDao(
        db: AppDatabase,
    ): ArticleDao {
        return db.articleDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, AppDatabase.name
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }
}