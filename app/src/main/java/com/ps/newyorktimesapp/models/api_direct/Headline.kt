package com.ps.newyorktimesapp.models.api_direct

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Headline(
    @Expose
    @SerializedName("main")
    var main: String? = null,

    @Expose
    @SerializedName("print_headline")
    var printHeadline: String? = null
)