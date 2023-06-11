package com.ps.newyorktimesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ps.newyorktimesapp.database.converter.ArticleDataConverter
import com.ps.newyorktimesapp.database.converter.CommonTypeConverters
import com.ps.newyorktimesapp.database.dao.NewsArticleDao
import com.ps.newyorktimesapp.database.models.QueryCache
import com.ps.newyorktimesapp.database.models.QueryCacheNewsArticleCrossRef
import com.ps.newyorktimesapp.models.ArticleData

@Database(
    entities = [ArticleData::class, QueryCache::class, QueryCacheNewsArticleCrossRef::class],
    version = NewsAppDatabase.DATABASE_VERSION
)
@TypeConverters(CommonTypeConverters::class, ArticleDataConverter::class)
abstract class NewsAppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "NewsAppDatabase"

        var INSTANCE: NewsAppDatabase? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    NewsAppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
            }
        }
    }

    abstract fun getNewsArticleDao(): NewsArticleDao
}