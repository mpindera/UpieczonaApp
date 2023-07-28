package com.example.upieczona.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PostsOfUpieczonaItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("date_gmt")
    val dateGmt: String,
    @SerializedName("guid")
    val guid: GuidDto,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("modified_gmt")
    val modifiedGmt: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("title")
    val title: TitleDto,
    @SerializedName("yoast_head_json")
    val yoastHeadJson: YoastHeadJsonDto
)