package com.ps.newyorktimesapp.models.api_direct

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Test")
data class Test(
    @PrimaryKey(autoGenerate = false)
    @Expose
    @SerializedName("_id")
    var id: String,

    @Expose
    @SerializedName("abstract")
    @ColumnInfo(name = "abstract")
    val abstract: String
) {
    constructor() : this("","")
}