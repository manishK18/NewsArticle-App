package com.ps.newyorktimesapp.database.models

import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["query", "id"], indices = [Index(value = ["headline"], unique = true)])
data class QueryCacheNewsArticleCrossRef(
    val query: String,
    val id: String,
    val headline: String
)