package com.ps.newyorktimesapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MetaData(
    @Expose
    @SerializedName("offset")
    val offset: Int,

    @Expose
    @SerializedName("limit")
    val limit: Int
)