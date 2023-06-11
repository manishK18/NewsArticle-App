package com.ps.newyorktimesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ps.newyorktimesapp.R
import com.ps.newyorktimesapp.ViewModelFactory
import com.ps.newyorktimesapp.adapters.UniversalRvAdapter
import com.ps.newyorktimesapp.databinding.SearchNewsArticlesFragmentBinding
import com.ps.newyorktimesapp.`interface`.OnSnapPositionChangeListener
import com.ps.newyorktimesapp.models.SearchArticleResponse
import com.ps.newyorktimesapp.network.RequestResult
import com.ps.newyorktimesapp.network.RequestType
import com.ps.newyorktimesapp.utils.PreferenceManager
import com.ps.newyorktimesapp.utils.attachSnapHelperWithListener
import com.ps.newyorktimesapp.utils.setVisibilityAndText
import com.ps.newyorktimesapp.viewmodels.SearchNewsArticlesFragmentVM


class SearchNewsArticlesFragment : Fragment(R.layout.search_news_articles_fragment),
    SearchView.OnQueryTextListener {

    private lateinit var binding: SearchNewsArticlesFragmentBinding
    private val mViewModel by lazy {
        ViewModelFactory().create(SearchNewsArticlesFragmentVM::class.java)
    }
    private val loadMoreDataMutableLd = MutableLiveData<Boolean>()
    private val rvAdapter by lazy {
        UniversalRvAdapter(loadMoreDataMutableLd = loadMoreDataMutableLd)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchNewsArticlesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        setupUI()
        mViewModel.searchArticles(
            mSearchQuery = mViewModel.getSearchQuery()
        )
    }

    private fun setupUI() {
        binding.apply {
            searchView.setOnQueryTextListener(this@SearchNewsArticlesFragment)
            detailNewsRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = rvAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        // Cached data is already fetched completely
                        if (PreferenceManager.isAppModeOnline()) {
                            if (!canScrollVertically(1) && mViewModel.isRequestInProgress().not()) {
                                mViewModel.addLoadMoreRvData(rvAdapter.getCurrentList())
                            }
                        }
                    }
                })
            }
            somethingWentWrongCl.btnRetry.setOnClickListener {
                mViewModel.searchArticles(
                    mSearchQuery = mViewModel.getSearchQuery(),
                    requestType = RequestType.RETRY
                )
            }
        }
    }

    private fun addObservers() {
        mViewModel.searchArticleLD.observe(viewLifecycleOwner, Observer {
            binding.somethingWentWrongCl.root.isVisible = false
            binding.progressBarLayout.isVisible = it.status == RequestResult.Status.LOADING
            when (it.status) {
                RequestResult.Status.SUCCESS -> {
                    // Submit list to recyclerview
                    initPageCountUI(it.data)
                }

                RequestResult.Status.ERROR -> {
                    // Show ncv also
                    binding.somethingWentWrongCl.root.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        "Error occurred - ${it.throwable?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {
                }
            }
        })

        mViewModel.uiListMutableLD.observe(viewLifecycleOwner) {
            rvAdapter.updateNewsListAndType(it ?: mutableListOf())
            binding.detailNewsRv.post {
                rvAdapter.notifyDataSetChanged()
            }
        }

        loadMoreDataMutableLd.observe(viewLifecycleOwner) {
            mViewModel.incrementPageNum()
            mViewModel.searchArticles(
                mSearchQuery = mViewModel.getSearchQuery(),
                requestType = RequestType.LOAD_MORE
            )
        }
    }

    private fun initPageCountUI(response: SearchArticleResponse?) {
        binding.pageActionLL.apply {
            tvCurrentPage.setVisibilityAndText(
                response?.currentPageNum?.toString() ?: mViewModel.getCurrentPageNum().toString()
            )
            btnPreviousPage.visibility =
                if (mViewModel.getCurrentPageNum() > 0) View.VISIBLE else View.INVISIBLE
            btnNextPage.visibility =
                if (mViewModel.getCurrentPageNum() < MAX_PAGE_COUNT) View.VISIBLE else View.INVISIBLE
            btnPreviousPage.setOnClickListener {
                mViewModel.decrementPageNum()
                mViewModel.searchArticles(mSearchQuery = mViewModel.getSearchQuery())
            }
            btnNextPage.setOnClickListener {
                mViewModel.incrementPageNum()
                mViewModel.searchArticles(mSearchQuery = mViewModel.getSearchQuery())
            }
        }
        binding.pageActionLL.root.isVisible = response != null
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        binding.searchTv.setVisibilityAndText(
            content = mViewModel.getSearchText(
                prefixString = requireContext().resources.getString(R.string.search_prefix),
                searchQuery = newText ?: ""
            )
        )
        mViewModel.searchArticles(mSearchQuery = newText ?: "", requestType = RequestType.SEARCH)
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onDestroyView() {
        loadMoreDataMutableLd.value = null
        super.onDestroyView()
    }

    companion object {
        const val NEWS_TYPE_TOP_STORIES = "TOP STORIES"
        const val NEWS_TYPE_TRENDING_STORIES = "TRENDING STORIES"
        private const val MAX_PAGE_COUNT = 100
    }
}
