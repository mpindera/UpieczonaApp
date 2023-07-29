package com.example.http

import com.example.http.dto2.CategoriesOfUpieczona
import com.example.upieczona.dto.PostsOfUpieczona
import retrofit2.http.GET
import retrofit2.http.Query

interface UpieczonaApi {
    @GET("wp-json/wp/v2/posts")
    suspend fun fetchAllNames(): PostsOfUpieczona

    @GET("wp-json/wp/v2/categories")
    suspend fun fetchAll(): CategoriesOfUpieczona

    @GET("wp-json/wp/v2/posts")
    suspend fun fetchCategoryPosts(@Query("categories") categoryId: Int): PostsOfUpieczona
}

