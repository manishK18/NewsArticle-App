package com.ps.newyorktimesapp

import android.app.Application
import com.ps.newyorktimesapp.database.NewsAppDatabase
import com.ps.newyorktimesapp.utils.PreferenceManager

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        NewsAppDatabase.init(context = applicationContext)
    }
}