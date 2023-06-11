package com.ps.newyorktimesapp.network

import android.app.Application
import com.ps.newyorktimesapp.repo.BaseRepository

abstract class BaseServiceLocator {
    abstract fun getDetailedNewsRepo(): BaseRepository
}
