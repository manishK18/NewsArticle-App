package com.ps.newyorktimesapp.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ps.newyorktimesapp.models.api_direct.ArticleDataAPI

@Entity(tableName = "NewsArticle")
data class NewsArticle(
    @PrimaryKey
    var newsId: String,

    @Embedded
    var article: ArticleDataAPI?
)