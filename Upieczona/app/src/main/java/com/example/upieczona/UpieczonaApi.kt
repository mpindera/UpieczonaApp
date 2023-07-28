package com.example.http

import com.example.http.dto2.CategoriesOfUpieczona
import com.example.upieczona.dto.PostsOfUpieczona
import retrofit2.http.GET

interface UpieczonaApi {
    @GET("wp-json/wp/v2/posts")
    suspend fun fetchAllNames(): PostsOfUpieczona

    @GET("wp-json/wp/v2/categories")
    suspend fun fetchAll(): CategoriesOfUpieczona
    
}

