package com.example.upieczona.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OgImageDto(
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("type")
    val type: String
)