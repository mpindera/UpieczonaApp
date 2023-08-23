package com.example.upieczona.dtoposts


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PostsOfUpieczonaItemDto(
    @SerializedName("author")
    val author: Int,
    @SerializedName("categories")
    val categories: List<Int>,
    @SerializedName("content")  //recipes & ingredients
    val content: ContentDto,
    @SerializedName("date")
    val date: String,
    @SerializedName("guid") // link with id
    val guid: GuidDto,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link") // link to site with slug
    val link: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tags") // ids tags
    val tags: List<Int>,
    @SerializedName("title")
    val title: TitleDto,
    @SerializedName("type")
    val type: String,
    @SerializedName("yoast_head_json")
    val yoastHeadJson: YoastHeadJsonDto
)