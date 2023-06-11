package com.ps.newyorktimesapp.models.recyclerview

data class NoMatchingSearchResultsRvData(
    val imageUrl: Int?,
    val title: String?,
    val actionBtnText: String? = null,
    val actionBtnCall: (() -> Unit)? = null
): RecyclerViewItem()