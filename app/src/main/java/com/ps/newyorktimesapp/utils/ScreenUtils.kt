package com.bharat.shortnews.utils

import android.content.res.Resources

object ScreenUtils {

    val width: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    val height: Int
        get() = Resources.getSystem().displayMetrics.heightPixels
}