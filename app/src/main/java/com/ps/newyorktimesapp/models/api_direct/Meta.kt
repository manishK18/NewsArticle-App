package com.ps.newyorktimesapp.models.api_direct

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Meta(
    @Expose
    @SerializedName("hits")
    val hits: Int?,

    @Expose
    @SerializedName("offset")
    val offset: Int?,

    @Expose
    @SerializedName("time")
    val time: Int?
)