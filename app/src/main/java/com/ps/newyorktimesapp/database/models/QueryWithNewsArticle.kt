package com.ps.newyorktimesapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.ps.newyorktimesapp.models.api_direct.ArticleDataAPI

data class QueryWithNewsArticle(
    @Embedded var queryCache: QueryCache,
    @Relation(
        parentColumn = "query",
        entityColumn = "newsId",
        associateBy = Junction(QueryCacheNewsArticleCrossRef::class)
    )
    var newsArticles: List<NewsArticle>
)