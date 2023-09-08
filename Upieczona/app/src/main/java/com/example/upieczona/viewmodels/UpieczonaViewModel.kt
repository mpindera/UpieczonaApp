package com.example.upieczona.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.upieczona.api.UpieczonaApi
import com.example.upieczona.dtocategories.CategoriesOfUpieczonaItemDto
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.dtoTags.TagsDto
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.staticobjects.MaterialsUtils.ingredientsListPattern
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


class UpieczonaViewModel(
  private val api: UpieczonaApi,
) : ViewModel() {

  var select = MutableStateFlow(0)

  val categoriesState = MutableStateFlow(emptyList<CategoriesOfUpieczonaItemDto>())

  val stateNames = MutableStateFlow(emptyList<PostsOfUpieczonaItemDto>())

  private val _allPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allPosts: State<List<PostsOfUpieczonaItemDto>> = _allPosts

  private val _allCategoryPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allCategoryPosts: State<List<PostsOfUpieczonaItemDto>> = _allCategoryPosts

  private val _error = MutableStateFlow<String?>(null)
  val error: StateFlow<String?> = _error

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

  val tagsUpieczona = MutableStateFlow(emptyList<TagsDto>())

  init {
    fetchData()
    fetchTags()
  }

  fun fetchTags() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val response = api.fetchTags()
        tagsUpieczona.value = response
      } catch (e: Exception) {
        Log.e("UpieczonaViewModel", "Error fetching data: ${e.message}")
      }
    }
  }


  fun fetchData() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _isLoading.value = true

        val response = api.fetchAllCategories()
        val responseNames = api.fetchAllPostsFromFirstPage()

        stateNames.value = responseNames
        categoriesState.value = response

      } catch (e: Exception) {
        Log.e("UpieczonaViewModel", "Error fetching data: ${e.message}")
      } finally {
        _isLoading.value = false
      }
    }
  }

  suspend fun fetchAllPosts() {
    try {
      _allPosts.value = emptyList()
      coroutineScope {
        var page = 1
        while (true) {
          val posts = api.fetchPostsFromFirstPage(page)

          if (posts.isNotEmpty()) {
            _allPosts.value = _allPosts.value + posts
            page++
          } else {
            break
          }
        }
      }
    } catch (e: Exception) {
      _error.value = "Error fetching post details"
      Log.e("UpieczonaViewModel", error.value.toString())
    }
  }

  suspend fun fetchAllPostsByCategory(categoryId: Int) {
    try {
      _allCategoryPosts.value = emptyList()
      var page = 1
      var posts: List<PostsOfUpieczonaItemDto>
      do {
        posts = api.fetchAllCategoryPosts(categoryId, page)
        _allCategoryPosts.value = _allCategoryPosts.value + posts
        page++
      } while (posts.isNotEmpty())
    } catch (e: Exception) {
      _error.value = "Error fetching post details"
      Log.e("UpieczonaViewModel", error.value.toString())
    }
  }



}
