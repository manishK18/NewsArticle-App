package com.ps.newyorktimesapp.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.google.gson.Gson

object CommonUtils {

    private var gson: Gson? = null

    fun openChromeTab(context: Context, url: String) {
        CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
    }

    fun getGsonInstance(): Gson {
        if (gson == null) gson = Gson()
        return gson!!
    }
}