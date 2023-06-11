package com.ps.newyorktimesapp.models.api_direct

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Multimedia(
    @Expose
    @SerializedName("crop_name")
    var cropName: String? = null,

    @Expose
    @SerializedName("subtype")
    var subType: String? = null,

    @Expose
    @SerializedName("type")
    var type: String? = null,

    @Expose
    @SerializedName("url")
    var url: String? = null
) {
    constructor(): this(null, null, null, null)

    companion object {
        const val MEDIA_PREFIX = "https://www.nytimes.com/"
        const val KEY_MEDIA_TYPE = "xlarge"
    }
}