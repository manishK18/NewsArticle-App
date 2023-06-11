package com.ps.newyorktimesapp.models.api_direct

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ArticleDataAPI(
    @Expose
    @SerializedName("abstract")
    var abstract: String? = null,

    @Expose
    @SerializedName("byline")
    var byline: Byline? = null,

    @Expose
    @SerializedName("document_type")
    var documentType: String? = null,

    @Expose
    @SerializedName("headline")
    var headline: Headline? = null,

    @Expose
    @SerializedName("_id")
    var id: String,

    @Expose
    @SerializedName("keywords_")
    @ColumnInfo(name = "keywords")
    var keywords: ArrayList<Keyword>? = null,

    @Expose
    @SerializedName("lead_paragraph")
    var leadParagraph: String? = null,

    @Expose
    @SerializedName("multimedia")
    var multimedia: ArrayList<Multimedia>? = null,

    @Expose
    @SerializedName("news_desk")
    var newsDesk: String? = null,

    @Expose
    @SerializedName("print_page")
    var printPage: String? = null,

    @Expose
    @SerializedName("print_section")
    var printSection: String? = null,

    @Expose
    @SerializedName("pub_date")
    var pubDate: String? = null,

    @Expose
    @SerializedName("section_name")
    var sectionName: String? = null,

    @Expose
    @SerializedName("snippet")
    var snippet: String? = null,

    @Expose
    @SerializedName("source")
    var source: String? = null,

    @Expose
    @SerializedName("subsection_name")
    var subsectionName: String? = null,

    @Expose
    @SerializedName("type_of_material")
    var typeOfMaterial: String? = null,

    @Expose
    @SerializedName("uri")
    var uri: String? = null,

    @Expose
    @SerializedName("web_url")
    var webUrl: String? = null,

    @Expose
    @SerializedName("word_count")
    var wordCount: Int? = null,

    @ColumnInfo(name = "card_type")
    var cardType: Int? = CARD_TYPE_ARTICLE
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        "",
        arrayListOf(),
        null,
        arrayListOf(),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    companion object {
        const val CARD_TYPE_ARTICLE = 0
        const val CARD_TYPE_AD = 1
    }
}