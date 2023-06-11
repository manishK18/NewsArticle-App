package com.ps.newyorktimesapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.ps.newyorktimesapp.ui.activity.BaseActivity

object PreferenceManager {
    private val KEY_IS_APP_MODE_ONLINE = "is_app_mode_online"
    private lateinit var sharedPref: SharedPreferences

    fun init(activity: BaseActivity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    }

    fun updateAppMode(isOffline: Boolean) {
        sharedPref.edit().putBoolean(KEY_IS_APP_MODE_ONLINE, isOffline)?.apply()
    }

    fun isAppModeOnline() = sharedPref.getBoolean(KEY_IS_APP_MODE_ONLINE, true)
}