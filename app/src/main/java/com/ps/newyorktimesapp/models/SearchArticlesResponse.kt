package com.ps.newyorktimesapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchArticleResponse(
    @Expose
    @SerializedName("status")
    val status: String?,

    @Expose
    @SerializedName("query")
    val query: String?,

    @Expose
    @SerializedName("articles")
    val articleDataList: List<ArticleData>?,

    @Expose
    @SerializedName("metaData")
    val metaData: MetaData? = null
)