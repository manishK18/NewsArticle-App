package com.ps.newyorktimesapp.models.recyclerview

data class NewsArticleRvData(
    val imageUrl: String?,
    val headline: String?,
    val description: String?,
    val publishedDate: String?,
    val keywords: String?,
    val webUrl: String?,
    val authorName: String?,
    val typeOfMaterial: String?
): RecyclerViewItem()