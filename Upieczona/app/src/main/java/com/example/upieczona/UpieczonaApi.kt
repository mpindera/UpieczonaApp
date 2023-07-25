package com.example.http

import com.example.http.dto.DTO
import com.example.http.dto2.CategoriesOfUpieczona
import retrofit2.http.GET

interface UpieczonaApi {
   /* @GET("wp-json/wp/v2/posts")
    suspend fun fetchAll(): DTO*/

    @GET("wp-json/wp/v2/categories")
    suspend fun fetchAll(): CategoriesOfUpieczona
}

