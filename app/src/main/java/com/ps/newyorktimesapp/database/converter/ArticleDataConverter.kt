package com.ps.newyorktimesapp.database.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.ps.newyorktimesapp.models.ArticleData
import com.ps.newyorktimesapp.utils.CommonUtils.getGsonInstance

class ArticleDataConverter {

    @TypeConverter
    fun fromArticleData(articleData: ArticleData): String {
        return getGsonInstance().toJson(articleData)
    }

    @TypeConverter
    fun toArticleData(json: String): ArticleData? {
        return getGsonInstance().fromJson(json, object : TypeToken<ArticleData>() {}.type)
    }
}