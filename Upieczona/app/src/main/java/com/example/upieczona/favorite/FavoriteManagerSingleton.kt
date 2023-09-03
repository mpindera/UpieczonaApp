package com.example.upieczona.favorite

object FavoriteManagerSingleton {
    private val favoritePostsMap = mutableMapOf<Int, FavoriteData>()

    fun getMap(): Map<Int, FavoriteData> {
        return favoritePostsMap.toMap()
    }

    fun addPostToMap(postId: Int, favoriteData: FavoriteData) {
        favoritePostsMap[postId] = favoriteData
    }

    fun removePostFromMap(postId: Int) {
        favoritePostsMap.remove(postId)
    }
}

