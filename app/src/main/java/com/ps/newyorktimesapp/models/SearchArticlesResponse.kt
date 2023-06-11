package com.ps.newyorktimesapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ps.newyorktimesapp.models.api_direct.Meta

data class SearchArticleResponse(
    @Expose
    @SerializedName("query")
    val query: String?,

    @Expose
    @SerializedName("results")
    val articleDataList: List<ArticleData>?,

    @Expose
    @SerializedName("meta")
    val meta: Meta?,

    @Expose
    @SerializedName("current_page_num")
    val currentPageNum: Int?,

    @Expose
    @SerializedName("next_page_num")
    val nextPageNum: Int?,

    @Expose
    @SerializedName("previous_page_num")
    val previousPageNum: Int?,

    @Expose
    @SerializedName("status")
    val status: String?
)