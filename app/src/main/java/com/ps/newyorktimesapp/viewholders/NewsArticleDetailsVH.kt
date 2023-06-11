package com.ps.newyorktimesapp.viewholders

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            imageUrl.takeIf { it.isNullOrBlank().not() }?.let {
                Glide.with(binding.root.context)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.thumbnailIv)
            } ?: kotlin.run {
                // Load default image if not available
                Log.d(Constants.KEY_LOGS_TAG, "Default image not available: $imageUrl")
            }

            binding.headlineTv.setVisibilityAndText(headline)
            binding.dateTimeTv.setVisibilityAndText(rvData.publishedDate)
            binding.detailTv.setVisibilityAndText(description)

            binding.keywordsTv.setVisibilityAndText(keywords)
            binding.openWebUrl.setOnClickListener { openWebUrl(webUrl) }
            binding.root.setOnClickListener { openWebUrl(webUrl) }
            binding.authorTv.setVisibilityAndText(authorName)
            binding.newsSrcTagTv.setVisibilityAndText(typeOfMaterial)






            /*val imageUrlSuffixPath =
                multimedia?.firstOrNull { it.subType == Multimedia.KEY_MEDIA_TYPE }?.url
            imageUrlSuffixPath.takeIf { it.isNullOrBlank().not() }?.let {
                Glide.with(binding.root.context)
                    .load(Multimedia.MEDIA_PREFIX + imageUrlSuffixPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.thumbnailIv)
            } ?: kotlin.run {
                // Load default image if not available
                Log.d(Constants.KEY_LOGS_TAG, "Default image not available: $imageUrlSuffixPath")
            }

            binding.headlineTv.text = headline?.main ?: ""
            rvData.pubDate?.let {
                binding.dateTimeTv.text = TimeUtils.getFormattedDateTime(rvData.pubDate)
            }
            binding.dateTimeTv.setVisibilityAndText(TimeUtils.getFormattedDateTime(rvData.pubDate))
            binding.detailTv.setVisibilityAndText(leadParagraph)

            binding.keywordsTv.setVisibilityAndText(
                this.keywords?.joinToString(
                    separator = ", ",
                    transform = { it.value?.capitalize() ?: "" },
                    limit = 5,
                    truncated = " etc."
                )
            )
            binding.openWebUrl.setOnClickListener { openWebUrl(webUrl) }
            binding.root.setOnClickListener { openWebUrl(webUrl) }
            binding.authorTv.setVisibilityAndText(byline.original)
            binding.newsSrcTagTv.setVisibilityAndText(typeOfMaterial)*/
        }
    }

    private fun openWebUrl(webUrl: String?) {
        webUrl?.let {
            openChromeTab(
                context = binding.root.context,
                url = webUrl
            )
        } ?: kotlin.run {
            Log.d(Constants.KEY_LOGS_TAG, "Web url is empty: ${webUrl}")
        }
    }
}