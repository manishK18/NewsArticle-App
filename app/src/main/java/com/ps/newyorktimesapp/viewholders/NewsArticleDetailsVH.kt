package com.ps.newyorktimesapp.viewholders

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ps.newyorktimesapp.R
import com.ps.newyorktimesapp.databinding.SingleDetailNewsArticleItemV2Binding
import com.ps.newyorktimesapp.models.recyclerview.NewsArticleRvData
import com.ps.newyorktimesapp.utils.CommonUtils.openChromeTab
import com.ps.newyorktimesapp.utils.Constants
import com.ps.newyorktimesapp.utils.setVisibilityAndText

class NewsArticleDetailsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: SingleDetailNewsArticleItemV2Binding

    @SuppressLint("DefaultLocale")
    fun bindItems(rvData: NewsArticleRvData?) {
        binding = SingleDetailNewsArticleItemV2Binding.bind(itemView)
        rvData?.apply {
            imageUrl?.let {
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.thumbnailIv)
            } ?: kotlin.run {
                binding.thumbnailIv.setBackgroundColor(binding.root.context.getColor(R.color.colorTertiary))
            }
            binding.headlineTv.setVisibilityAndText(headline)
            binding.dateTimeTv.setVisibilityAndText(rvData.publishedDate)
            binding.detailTv.setVisibilityAndText(description)
            binding.keywordsTv.setVisibilityAndText(keywords)
            binding.openWebUrl.setOnClickListener { openWebUrl(webUrl) }
            binding.root.setOnClickListener { openWebUrl(webUrl) }
            binding.authorTv.setVisibilityAndText(authorName)
            binding.newsSrcTagTv.setVisibilityAndText(typeOfMaterial)
        }
    }

    private fun openWebUrl(webUrl: String?) {
        webUrl.takeIf { it.isNullOrBlank().not() }?.let {
            openChromeTab(
                context = binding.root.context,
                url = it
            )
        } ?: kotlin.run {
            Log.d(Constants.KEY_LOGS_TAG, "Web url is empty: $webUrl")
        }
    }
}