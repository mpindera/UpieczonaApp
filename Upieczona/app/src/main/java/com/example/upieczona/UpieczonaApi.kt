package com.example.http

import com.example.upieczona.dtocategories.CategoriesOfUpieczona
import com.example.upieczona.dtoposts.PostsOfUpieczona
import retrofit2.http.GET
import retrofit2.http.Query

interface UpieczonaApi {
    @GET("wp-json/wp/v2/posts")
    suspend fun fetchAllNames(): PostsOfUpieczona

    @GET("wp-json/wp/v2/categories")
    suspend fun fetchAll(): CategoriesOfUpieczona

    @GET("wp-json/wp/v2/posts")
    suspend fun fetchPostsFromFirstPage(@Query("page") categoryId: Int): PostsOfUpieczona

    @GET("wp-json/wp/v2/posts")
    suspend fun fetchCategoryPosts(
        @Query("categories") categoryId: Int,
        @Query("page") page: Int
    ): PostsOfUpieczona
}

