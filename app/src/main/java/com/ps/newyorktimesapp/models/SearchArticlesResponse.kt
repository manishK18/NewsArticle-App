package com.ps.newyorktimesapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchArticleResponse(
    @Expose
    @SerializedName("query")
    val query: String?,

    @Expose
    @SerializedName("results")
    val articleDataList: List<ArticleData>?,

    @Expose
    @SerializedName("meta")
    val metaData: MetaData? = null,

    @Expose
    @SerializedName("status")
    val status: String?
)