package com.ps.newyorktimesapp.network

import com.ps.newyorktimesapp.database.dao.NewsArticleDao
import com.ps.newyorktimesapp.repo.BaseRepository
import com.ps.newyorktimesapp.repo.SearchNewsFragmentRepo

class UserServiceLocator : BaseServiceLocator() {

    private val api by lazy {
        NewsArticleSearchAPI.create()
    }

    private val localDataSource by lazy {
        NewsArticleDao.getInstance()
    }

    override fun getDetailedNewsRepo(): BaseRepository {
        return SearchNewsFragmentRepo(api, localDataSource)
    }
}
