package com.ps.newyorktimesapp.models.api_direct

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Legacy(
    @Expose
    @SerializedName("thumbnail")
    val thumbnail: String?,

    @Expose
    @SerializedName("thumbnailheight")
    val thumbnailHeight: Int?,

    @Expose
    @SerializedName("thumbnailwidth")
    val thumbnailWidth: Int?,

    @Expose
    @SerializedName("wide")
    val wide: String?,

    @Expose
    @SerializedName("wideheight")
    val wideHeight: Int?,

    @Expose
    @SerializedName("widewidth")
    val wideWidth: Int?,

    @Expose
    @SerializedName("xlarge")
    val xLarge: String?,

    @Expose
    @SerializedName("xlargeheight")
    val xLargeHeight: Int?,

    @Expose
    @SerializedName("xlargewidth")
    val xLargeWidth: Int?
)