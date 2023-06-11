package com.ps.newyorktimesapp.models.api_direct

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchArticlesResponseAPI(
    @Expose
    @SerializedName("response")
    val response: Response?,

    @Expose
    @SerializedName("status")
    val status: String?
)

data class Response(
    @Expose
    @SerializedName("docs")
    val articleDataList: List<ArticleDataAPI>?,

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
    val previousPageNum: Int?
)