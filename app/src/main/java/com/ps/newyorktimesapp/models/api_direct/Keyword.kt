package com.ps.newyorktimesapp.models.api_direct

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Keyword(
    @Expose
    @SerializedName("major")
    var major: String? = null,

    @Expose
    @SerializedName("name")
    var name: String? = null,

    @Expose
    @SerializedName("rank")
    var rank: Int? = null,

    @Expose
    @SerializedName("value")
    var value: String? = null
) {
    constructor() : this(null, null, null, null)
}