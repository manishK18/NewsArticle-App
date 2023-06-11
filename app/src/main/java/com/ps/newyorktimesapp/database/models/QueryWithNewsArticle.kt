package com.ps.newyorktimesapp.database.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ps.newyorktimesapp.models.ArticleData

data class QueryWithNewsArticle(
    @Embedded var queryCache: QueryCache,
    @Relation(
        parentColumn = "query",
        entityColumn = "id",
        associateBy = Junction(QueryCacheNewsArticleCrossRef::class)
    )
    var newsArticles: List<ArticleData>
)