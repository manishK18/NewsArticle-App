package com.ps.newyorktimesapp.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QueryCache")
data class QueryCache(
    @PrimaryKey
    var query: String
)