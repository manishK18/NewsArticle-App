package com.ps.newyorktimesapp.viewmodels

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.newyorktimesapp.R
import com.ps.newyorktimesapp.constants.RequestParamConstants
import com.ps.newyorktimesapp.models.ArticleData
import com.ps.newyorktimesapp.models.SearchArticleResponse
import com.ps.newyorktimesapp.models.recyclerview.LoadMoreRvData
import com.ps.newyorktimesapp.models.recyclerview.NewsArticleRvData
import com.ps.newyorktimesapp.models.recyclerview.NoMatchingSearchResultsRvData
import com.ps.newyorktimesapp.models.recyclerview.RecyclerViewItem
import com.ps.newyorktimesapp.models.recyclerview.StartSearchRvData
import com.ps.newyorktimesapp.network.RequestResult
import com.ps.newyorktimesapp.network.RequestType
import com.ps.newyorktimesapp.repo.SearchNewsFragmentRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsArticlesFragmentVM(
    private val repo: SearchNewsFragmentRepo
) : ViewModel() {
    private val _searchArticleMutableLD = MutableLiveData<RequestResult<SearchArticleResponse?>>()
    val searchArticleLD: LiveData<RequestResult<SearchArticleResponse?>> = _searchArticleMutableLD

    private val _uiListMutableLD = MutableLiveData<List<RecyclerViewItem>?>()
    val uiListMutableLD: LiveData<List<RecyclerViewItem>?> = _uiListMutableLD

    private var searchArticleJob: Job? = null
    private var searchQuery: String? = null
    private var queryPageOffset = DEFAULT_QUERY_PAGE_OFFSET.toString()
    private var queryPageLimit = DEFAULT_QUERY_PAGE_LIMIT

    fun searchArticles(mSearchQuery: String?, requestType: RequestType = RequestType.NORMAL) {
        searchArticleJob?.cancel()
        searchArticleJob = viewModelScope.launch {
            if (mSearchQuery.isNullOrBlank().not() && requestType == RequestType.SEARCH) {
                delay(QUERY_DELAY_MS)
            }
            searchQuery = mSearchQuery
            mSearchQuery.takeIf { it.isNullOrBlank().not() }?.let { mSearchQuery ->
                if (requestType != RequestType.LOAD_MORE) {
                    _searchArticleMutableLD.value = RequestResult.loading(null)
                    _uiListMutableLD.value = mutableListOf()
                }
                repo.getSearchArticlesNYTAPI(
                    getQueryRequestMap(requestType = requestType, query = mSearchQuery)
                ).onSuccess {
                    queryPageOffset = it.metaData?.offset?.toString() ?: DEFAULT_QUERY_PAGE_OFFSET
                    queryPageLimit = it.metaData?.limit?.toString() ?: DEFAULT_QUERY_PAGE_LIMIT
                    if (requestType == RequestType.LOAD_MORE) {
                        _uiListMutableLD.value =
                            _uiListMutableLD.value?.toMutableList()?.also { oldList ->
                                // Removing the LoadMoreVH
                                oldList.subList(0, oldList.size - 1)
                                    .addAll(curateRvDataList(it.articleDataList))
                            }
                    } else {
                        _uiListMutableLD.value = curateRvDataList(it.articleDataList)
                    }
                    _searchArticleMutableLD.value = RequestResult.success(it)
                }.onFailure {
                    println(it.toString())
                    _searchArticleMutableLD.value = RequestResult.error(throwable = it)
                    _uiListMutableLD.value = mutableListOf()
                }
            } ?: kotlin.run {
                resetData()
                _searchArticleMutableLD.value = RequestResult.success(null)
                _uiListMutableLD.value = curateRvDataList()
            }
        }
    }

    private fun getQueryRequestMap(requestType: RequestType, query: String): HashMap<String, String> {
        val requestMap: HashMap<String, String> = HashMap()
        requestMap[RequestParamConstants.KEY_REQUEST_PARAM_OFFSET] =
            if (requestType != RequestType.LOAD_MORE) DEFAULT_QUERY_PAGE_OFFSET
            else queryPageOffset
        requestMap[RequestParamConstants.KEY_REQUEST_PARAM_LIMIT] = queryPageLimit
        requestMap[RequestParamConstants.KEY_REQUEST_PARAM_QUERY] = query
        return requestMap
    }

    private fun curateRvDataList(articlesDataList: List<ArticleData>? = null): List<RecyclerViewItem> {
        val rvDataList = mutableListOf<RecyclerViewItem>()
        articlesDataList?.forEach {
            rvDataList.add(
                NewsArticleRvData(
                    imageUrl = it.imageUrl,
                    headline = it.headline,
                    description = it.description,
                    publishedDate = it.publishedDate,
                    keywords = it.keywords,
                    webUrl = it.webUrl,
                    authorName = it.authorName,
                    typeOfMaterial = it.typeOfMaterial
                )
            )
        }
        if (rvDataList.isEmpty()) {
            if (searchQuery.isNullOrBlank().not()) {
                rvDataList.add(
                    getNoMatchingResultRvData()
                )
            } else {
                rvDataList.add(
                    getStartSearchRvData()
                )
            }
        }
        return rvDataList
    }

    private fun getNoMatchingResultRvData(): NoMatchingSearchResultsRvData {
        return NoMatchingSearchResultsRvData(
            imageUrl = R.drawable.no_matching_results_image,
            title = "No matching results found, please try searching something else"
        )
    }

    private fun getStartSearchRvData(): StartSearchRvData {
        return StartSearchRvData(
            title = "Start searching news articles from New York Times API"
        )
    }

    private fun getLoadMoreRvData(): LoadMoreRvData {
        return LoadMoreRvData(isLoading = true)
    }

    fun getSearchText(prefixString: String, searchQuery: String): CharSequence {
        return SpannableStringBuilder(prefixString).append(" $searchQuery").apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                prefixString.length,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun resetData() {
        searchQuery = ""
    }

    fun addLoadMoreRvData(currentList: List<RecyclerViewItem>) {
        _uiListMutableLD.value = currentList.toMutableList().also {
            it.add(
                getLoadMoreRvData()
            )
        }
    }

    fun getSearchQuery() = searchQuery

    fun isRequestInProgress(): Boolean {
        return searchArticleJob?.isActive == true
    }

    companion object {
        private const val QUERY_DELAY_MS = 500L
        private const val DEFAULT_QUERY_PAGE_OFFSET = "0"
        private const val DEFAULT_QUERY_PAGE_LIMIT = "20"
    }
}
