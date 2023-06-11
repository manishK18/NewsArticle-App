package com.ps.newyorktimesapp.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "NewsArticle", indices = [Index(value = ["headline"], unique = true)])
data class ArticleData(
    @PrimaryKey
    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("imageUrl")
    val imageUrl: String?,

    @Expose
    @SerializedName("headline")
    val headline: String?,

    @Expose
    @SerializedName("description")
    val description: String?,

    @Expose
    @SerializedName("publishedDate")
    val publishedDate: String?,

    @Expose
    @SerializedName("keywords")
    val keywords: String?,

    @Expose
    @SerializedName("webUrl")
    val webUrl: String?,

    @Expose
    @SerializedName("authorName")
    val authorName: String?,

    @Expose
    @SerializedName("typeOfMaterial")
    val typeOfMaterial: String,

    val cardType: Int = CARD_TYPE_ARTICLE
) {
    companion object {
        const val CARD_TYPE_ARTICLE = 0
    }
}