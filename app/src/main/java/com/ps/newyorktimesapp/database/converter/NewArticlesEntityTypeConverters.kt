package com.ps.newyorktimesapp.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ps.newyorktimesapp.models.api_direct.Byline
import com.ps.newyorktimesapp.models.api_direct.Headline
import com.ps.newyorktimesapp.models.api_direct.Keyword
import com.ps.newyorktimesapp.models.api_direct.Multimedia
import com.ps.newyorktimesapp.utils.CommonUtils.getGsonInstance
import java.util.ArrayList


class NewArticlesEntityTypeConverters {

    @TypeConverter
    fun fromMultimedia(multimedia: ArrayList<Multimedia>?): String? {
        return getGsonInstance().toJson(multimedia)
    }

    @TypeConverter
    fun toMultimedia(json: String?): ArrayList<Multimedia>? {
        return getGsonInstance().fromJson(json, object : TypeToken<ArrayList<Multimedia>?>() {}.type)
    }

    @TypeConverter
    fun toByline(byline: Byline?): String? {
        return getGsonInstance().toJson(byline)
    }

    @TypeConverter
    fun fromByline(json: String?): Byline? {
        return getGsonInstance().fromJson(json, object : TypeToken<Byline?>() {}.type)
    }

    @TypeConverter
    fun fromHeadline(headline: Headline?): String? {
        return getGsonInstance().toJson(headline)
    }

    @TypeConverter
    fun toHeadline(json: String?): Headline? {
        return getGsonInstance().fromJson(json, object : TypeToken<Headline?>() {}.type)
    }

    @TypeConverter
    fun fromKeywords(keywords: ArrayList<Keyword>?): String? {
        return getGsonInstance().toJson(keywords)
    }

    @TypeConverter
    fun toKeywords(json: String?): ArrayList<Keyword>? {
        return getGsonInstance().fromJson(json, object : TypeToken<ArrayList<Keyword>?>() {}.type)
    }
}