package com.ps.newyorktimesapp.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ps.newyorktimesapp.databinding.LoadMoreViewBinding
import com.ps.newyorktimesapp.databinding.StartSearchViewBinding
import com.ps.newyorktimesapp.models.recyclerview.RecyclerViewItem
import com.ps.newyorktimesapp.models.recyclerview.StartSearchRvData
import com.ps.newyorktimesapp.utils.setVisibilityAndText

class LoadMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: LoadMoreViewBinding

    @SuppressLint("DefaultLocale")
    fun bindItems() {
        binding = LoadMoreViewBinding.bind(itemView)
    }
}