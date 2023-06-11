package com.ps.newyorktimesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ps.newyorktimesapp.R
import com.ps.newyorktimesapp.models.recyclerview.LoadMoreRvData
import com.ps.newyorktimesapp.models.recyclerview.NewsArticleRvData
import com.ps.newyorktimesapp.models.recyclerview.NoMatchingSearchResultsRvData
import com.ps.newyorktimesapp.models.recyclerview.RecyclerViewItem
import com.ps.newyorktimesapp.models.recyclerview.StartSearchRvData
import com.ps.newyorktimesapp.viewholders.LoadMoreVH
import com.ps.newyorktimesapp.viewholders.NewsArticleDetailsVH
import com.ps.newyorktimesapp.viewholders.NoMatchingResultsFoundVH
import com.ps.newyorktimesapp.viewholders.StartSearchVH

class UniversalRvAdapter(
    private val rvDataList: MutableList<RecyclerViewItem> = mutableListOf(),
    private val loadMoreDataMutableLd: MutableLiveData<Boolean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NO_MATCHING_RESULTS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.no_matching_results_found_view, parent, false)
                NoMatchingResultsFoundVH(view)
            }

            VIEW_TYPE_START_SEARCH -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.start_search_view, parent, false)
                StartSearchVH(view)
            }

            VIEW_TYPE_LOAD_MORE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.load_more_view, parent, false)
                LoadMoreVH(view)
            }

            else -> {
                // Only VIEW_TYPE_NEWS_ARTICLE
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.single_detail_news_article_item_v2, parent, false)
                NewsArticleDetailsVH(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (rvDataList[position]) {
            is NoMatchingSearchResultsRvData -> VIEW_TYPE_NO_MATCHING_RESULTS
            is StartSearchRvData -> VIEW_TYPE_START_SEARCH
            is LoadMoreRvData -> VIEW_TYPE_LOAD_MORE
            else -> {
                // NewsArticleRvData
                VIEW_TYPE_NEWS_ARTICLE
            }
        }
    }

    override fun getItemCount(): Int {
        return rvDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (rvDataList[position]) {
            is NoMatchingSearchResultsRvData -> (holder as NoMatchingResultsFoundVH).bindItems(
                rvData = rvDataList[position] as? NoMatchingSearchResultsRvData
            )
            is StartSearchRvData -> (holder as StartSearchVH).bindItems(
                rvData = rvDataList[position] as? StartSearchRvData
            )
            is LoadMoreRvData -> {
                (holder as LoadMoreVH).bindItems()
                loadMoreDataMutableLd.value = true
            }
            else -> {
                // NewsArticleRvData
                (holder as NewsArticleDetailsVH).bindItems(
                    rvData = rvDataList[position] as? NewsArticleRvData
                )
            }
        }
    }

    fun updateNewsListAndType(
        updatedRvDataList: List<RecyclerViewItem>,
        shouldReplaceList: Boolean = true
    ) {
        if (shouldReplaceList) {
            rvDataList.clear()
        }
        rvDataList.addAll(updatedRvDataList)
    }

    fun getCurrentList() = rvDataList

    companion object {
        private const val VIEW_TYPE_NEWS_ARTICLE = 1
        private const val VIEW_TYPE_NO_MATCHING_RESULTS = 2
        private const val VIEW_TYPE_START_SEARCH = 3
        private const val VIEW_TYPE_LOAD_MORE = 4
    }
}
