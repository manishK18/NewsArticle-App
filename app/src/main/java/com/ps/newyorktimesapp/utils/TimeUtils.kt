package com.bharat.shortnews.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object TimeUtils {
    private const val DATE_TIME_INPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ"
    private const val DATE_TIME_OUTPUT_PATTERN = "yyyy-MM-dd HH:mm a"

    //2012-01-01T00:00:00Z
    @SuppressLint("SimpleDateFormat")
    fun getFormattedDateTime(dateTimeString: String?): String? {
        if (dateTimeString.isNullOrBlank()) return dateTimeString
        val format = SimpleDateFormat(DATE_TIME_INPUT_PATTERN)
        val parsedDate = format.parse(dateTimeString);
        val outputFormat = SimpleDateFormat(DATE_TIME_OUTPUT_PATTERN, Locale.US)
        outputFormat.timeZone = TimeZone.getDefault()
        return parsedDate?.let { outputFormat.format(it) } ?: ""
    }

}