package com.ps.newyorktimesapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["query", "newsId"])
data class QueryCacheNewsArticleCrossRef(
    val query: String,
    val newsId: String
)