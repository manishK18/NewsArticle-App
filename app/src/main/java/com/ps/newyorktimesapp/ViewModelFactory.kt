package com.ps.newyorktimesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ps.newyorktimesapp.network.UserServiceLocator
import com.ps.newyorktimesapp.repo.SearchNewsFragmentRepo
import com.ps.newyorktimesapp.viewmodels.SearchNewsArticlesFragmentVM

class ViewModelFactory: ViewModelProvider.Factory {

    private val detailedNewsFragmentRepo by lazy {
        UserServiceLocator().getDetailedNewsRepo()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SearchNewsArticlesFragmentVM::class.java) -> return SearchNewsArticlesFragmentVM(
                detailedNewsFragmentRepo as SearchNewsFragmentRepo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}