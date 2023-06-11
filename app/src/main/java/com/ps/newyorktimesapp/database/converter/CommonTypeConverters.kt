package com.ps.newyorktimesapp.database.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.ps.newyorktimesapp.utils.CommonUtils.getGsonInstance

class CommonTypeConverters {

    @TypeConverter
    fun fromStringArray(array: List<String>?): String {
        return getGsonInstance().toJson(array)
    }

    @TypeConverter
    fun toStringArray(json: String): List<String>? {
        return getGsonInstance().fromJson(json, object : TypeToken<List<String>?>() {}.type)
    }
}