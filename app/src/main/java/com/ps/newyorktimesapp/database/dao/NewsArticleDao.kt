package com.ps.newyorktimesapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ps.newyorktimesapp.database.NewsAppDatabase
import com.ps.newyorktimesapp.database.models.QueryCache
import com.ps.newyorktimesapp.database.models.QueryCacheNewsArticleCrossRef
import com.ps.newyorktimesapp.database.models.QueryWithNewsArticle
import com.ps.newyorktimesapp.models.ArticleData

@Dao
interface NewsArticleDao {

    companion object {
        fun getInstance() = NewsAppDatabase.INSTANCE?.getNewsArticleDao()
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsArticle(newsArticle: ArticleData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuery(queryCache: QueryCache)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQueryWithNewsArticles(queryCacheNewsArticleCrossRef: QueryCacheNewsArticleCrossRef)

    @Transaction
    @Query("Select * from QueryCache where `query` = :query")
    suspend fun getNewsArticles(query: String): List<QueryWithNewsArticle>








//    @Query("Insert into NewsArticle (id, article_, article_webUrl) Values(:articleId, :articleData)")
//    suspend fun insertNewsArticle(articleId: String, articleData: ArticleDataAPI)
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNewsArticle(articleId: String, articleData: ArticleDataAPI)
//
//    @Query("select * from QueryCache WHERE `query` = :query")
//    suspend fun getNewsIdsListForQuery(query: String): List<String>
//
//    @Query("UPDATE QueryCache SET newsIdList = :articlesId WHERE `query` = :query")
//    suspend fun insertNewsArticlesIds(query: String, articlesId: List<String>)
}