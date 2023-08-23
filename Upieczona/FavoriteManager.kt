package com.example.upieczona

import android.content.Context
import androidx.preference.PreferenceManager

class FavoriteManager(private val context: Context) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val FAVORITE_KEY = "favorite_posts"

    private val favoritePostsMap: MutableMap<String, String>
        get() {
            val favorites = sharedPreferences.getStringSet(FAVORITE_KEY, emptySet()) ?: emptySet()
            val favoritesMap = mutableMapOf<String, String>()
            for (item in favorites) {
                val parts = item.split(',')
                if (parts.size == 2) {
                    val postId = parts[0]
                    val imageUrl = parts[1]
                    favoritesMap[postId] = imageUrl
                }
            }
            return favoritesMap
        }

    fun getFavoritePosts(): Map<String, String> {
        return favoritePostsMap.toMap()
    }

    fun addFavoritePost(postId: String, imageUrl: String) {
        val favoritesMap = favoritePostsMap.toMutableMap()
        favoritesMap[postId] = imageUrl
        val favoritesSet = favoritesMap.map { "${it.key},${it.value}" }.toSet()
        sharedPreferences.edit().putStringSet(FAVORITE_KEY, favoritesSet).apply()
    }


    fun removeFavoritePost(postId: String) {
        val favoritesMap = favoritePostsMap.toMutableMap()
        favoritesMap.remove(postId)
        val favoritesSet = favoritesMap.map { "${it.key},${it.value}" }.toSet()
        sharedPreferences.edit().putStringSet(FAVORITE_KEY, favoritesSet).apply()
    }

    fun isPostFavorite(postId: String): Boolean {
        return favoritePostsMap.containsKey(postId)
    }
}
