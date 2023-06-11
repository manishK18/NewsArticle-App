package com.ps.newyorktimesapp.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ps.newyorktimesapp.databinding.NoMatchingResultsFoundViewBinding
import com.ps.newyorktimesapp.models.recyclerview.NoMatchingSearchResultsRvData
import com.ps.newyorktimesapp.utils.setVisibilityAndText

class NoMatchingResultsFoundVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: NoMatchingResultsFoundViewBinding

    @SuppressLint("DefaultLocale")
    fun bindItems(rvData: NoMatchingSearchResultsRvData?) {
        binding = NoMatchingResultsFoundViewBinding.bind(itemView)
        binding.apply {
            titleTv.setVisibilityAndText(rvData?.title)
        }
    }
}