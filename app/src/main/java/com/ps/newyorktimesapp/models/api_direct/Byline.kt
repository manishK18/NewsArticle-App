package com.ps.newyorktimesapp.models.api_direct

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Byline(
    @Expose
    @SerializedName("original")
    var original: String? = null
)