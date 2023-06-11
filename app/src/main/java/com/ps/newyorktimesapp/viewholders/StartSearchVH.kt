package com.ps.newyorktimesapp.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ps.newyorktimesapp.databinding.StartSearchViewBinding
import com.ps.newyorktimesapp.models.recyclerview.StartSearchRvData
import com.ps.newyorktimesapp.utils.setVisibilityAndText

class StartSearchVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: StartSearchViewBinding

    @SuppressLint("DefaultLocale")
    fun bindItems(rvData: StartSearchRvData?) {
        binding = StartSearchViewBinding.bind(itemView)
        binding.apply {
            titleTv.setVisibilityAndText(rvData?.title)
        }
    }
}