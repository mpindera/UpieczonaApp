package com.example.upieczona.favorite

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.preference.PreferenceManager

data class FavoriteData(val postName: String, val postImageUrl: String)
class FavoriteManager(private val context: Context) {
  private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
  private val FAVORITE_KEY = "favorite_posts"

  private val favoritePostsMap: MutableMap<Int, FavoriteData> = loadFavoritePosts()
  val favoritePostsState: MutableState<Map<Int, FavoriteData>> = mutableStateOf(favoritePostsMap)

  private fun loadFavoritePosts(): MutableMap<Int, FavoriteData> {
    val favoritesSet = sharedPreferences.getStringSet(FAVORITE_KEY, emptySet()) ?: emptySet()
    val map = mutableMapOf<Int, FavoriteData>()
    for (favoriteItem in favoritesSet) {
      val parts = favoriteItem.split(",")
      if (parts.size == 3) {
        val postId = parts[0].toInt()
        val postName = parts[1]
        val postImageUrl = parts[2]
        map[postId] = FavoriteData(postName, postImageUrl)
      }
    }
    return map
  }

  fun getFavoritePosts(): Map<Int, FavoriteData> {
    return favoritePostsMap.toMap()
  }

  fun addFavoritePost(postId: Int, postName: String, postImageUrl: String) {
    val favoriteDataPass = FavoriteData(postName = postName, postImageUrl = postImageUrl)
    favoritePostsMap[postId] = favoriteDataPass
    updateSharedPreferences()
    favoritePostsState.value = favoritePostsMap.toMap()
  }

  fun removeFavoritePost(postId: Int) {
    favoritePostsMap.remove(postId)
    updateSharedPreferences()
    favoritePostsState.value = favoritePostsMap.toMap()
  }

  private fun updateSharedPreferences() {
    val favoritesSet =
      favoritePostsMap.map { "${it.key},${it.value.postName},${it.value.postImageUrl}" }
        .toSet()
    sharedPreferences.edit().putStringSet(FAVORITE_KEY, favoritesSet).apply()
  }

  fun isPostFavorite(postId: Int): Boolean {
    return favoritePostsMap.containsKey(postId)
  }
}
