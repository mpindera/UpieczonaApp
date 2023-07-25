package com.example.http.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GuidDto(
    @SerializedName("rendered")
    val rendered: String
)