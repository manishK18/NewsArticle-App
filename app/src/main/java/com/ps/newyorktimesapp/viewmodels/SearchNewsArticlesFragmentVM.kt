package com.ps.newyorktimesapp.viewmodels

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharat.shortnews.utils.TimeUtils
import com.ps.newyorktimesapp.R
import com.ps.newyorktimesapp.models.api_direct.ArticleDataAPI
import com.ps.newyorktimesapp.models.api_direct.Multimedia
import com.ps.newyorktimesapp.models.api_direct.SearchArticlesResponseAPI
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
    var requestType = RequestType.NORMAL
    private val _searchArticleMutableLD = MutableLiveData<RequestResult<SearchArticlesResponseAPI?>>()
    val searchArticleLD: LiveData<RequestResult<SearchArticlesResponseAPI?>> = _searchArticleMutableLD

    private val _uiListMutableLD = MutableLiveData<List<RecyclerViewItem>?>()
    val uiListMutableLD: LiveData<List<RecyclerViewItem>?> = _uiListMutableLD

    private var searchArticleJob: Job? = null
    private var pageNum: Int = 0
    private var searchQuery: String? = null

    fun searchArticles(mSearchQuery: String?, requestType: RequestType = RequestType.NORMAL) {
        searchArticleJob?.cancel()
        searchArticleJob = viewModelScope.launch {
            if (mSearchQuery.isNullOrBlank().not() && requestType == RequestType.SEARCH) {
                delay(QUERY_DELAY_MS)
            }
            searchQuery = mSearchQuery
            if (searchQuery.isNullOrBlank().not()) {
                if (requestType != RequestType.LOAD_MORE) {
                    _searchArticleMutableLD.value = RequestResult.loading(null)
                    _uiListMutableLD.value = mutableListOf()
                }
                repo.getSearchArticlesNYTAPI(query = searchQuery ?: "", pageNum = pageNum.toString())
                    .onSuccess {
                        println(it.toString())
                        if (requestType == RequestType.LOAD_MORE) {
                            _uiListMutableLD.value =
                                _uiListMutableLD.value?.toMutableList()?.also { oldList ->
                                    // Removing the LoadMoreVH
                                    oldList.subList(0, oldList.size - 1)
                                        .addAll(curateRvDataList(it.response?.articleDataList))
                                }
                        } else {
                            _uiListMutableLD.value = curateRvDataList(it.response?.articleDataList)
                        }
                        _searchArticleMutableLD.value = RequestResult.success(it)
                    }.onFailure {
                        println(it.toString())
                        _searchArticleMutableLD.value = RequestResult.error(throwable = it)
                        _uiListMutableLD.value = mutableListOf()
                    }
            } else {
                resetData()
                _searchArticleMutableLD.value = RequestResult.success(null)
                _uiListMutableLD.value = curateRvDataList()
            }
        }
    }

    private fun curateRvDataList(articlesDataList: List<ArticleDataAPI>? = null): List<RecyclerViewItem> {
        val rvDataList = mutableListOf<RecyclerViewItem>()
        articlesDataList?.forEach {
            rvDataList.add(
                NewsArticleRvData(
                    imageUrl = getImageUrlCompletePath(it),
                    headline = it.headline?.main,
                    description = it.leadParagraph,
                    publishedDate = TimeUtils.getFormattedDateTime(it.pubDate),
                    keywords = it.keywords?.joinToString(
                        separator = ", ",
                        transform = { it.value?.capitalize() ?: "" },
                        limit = 5,
                        truncated = " etc."
                    ),
                    webUrl = it.webUrl,
                    authorName = it.byline?.original,
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

    private fun getImageUrlCompletePath(articleData: ArticleDataAPI): String? {
        val prefixPath =
            articleData.multimedia?.firstOrNull { it.subType == Multimedia.KEY_MEDIA_TYPE }?.url
        if (prefixPath.isNullOrBlank()) return null
        return Multimedia.MEDIA_PREFIX + prefixPath
    }

    private fun resetData() {
        pageNum = 0
        searchQuery = ""
    }

    fun addLoadMoreRvData(currentList: List<RecyclerViewItem>) {
        _uiListMutableLD.value = currentList.toMutableList().also {
            it.add(
                getLoadMoreRvData()
            )
        }
    }

    fun incrementPageNum() = pageNum++
    fun decrementPageNum() = pageNum--
    fun getSearchQuery() = searchQuery
    fun getCurrentPageNum() = pageNum

    companion object {
        private const val QUERY_DELAY_MS = 500L
    }
}
