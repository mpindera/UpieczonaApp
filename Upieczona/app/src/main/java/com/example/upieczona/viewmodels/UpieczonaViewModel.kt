package com.example.upieczona.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.http.UpieczonaApi
import com.example.http.dto2.CategoriesOfUpieczonaItemDto
import com.example.upieczona.dto.PostsOfUpieczonaItemDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpieczonaViewModel(
    private val api: UpieczonaApi,
) : ViewModel() {

    val state2 = MutableStateFlow(emptyList<CategoriesOfUpieczonaItemDto>())

    val firstStateNames = MutableStateFlow(emptyList<PostsOfUpieczonaItemDto>())

    private val _allPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
    val allPosts: State<List<PostsOfUpieczonaItemDto>> = _allPosts

    private val _allCategoryPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
    val allCategoryPosts: State<List<PostsOfUpieczonaItemDto>> = _allCategoryPosts


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true

                val response = api.fetchAll()
                val responseNames = api.fetchAllNames()

                firstStateNames.value = responseNames
                state2.value = response

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
            var page = 1
            var posts: List<PostsOfUpieczonaItemDto>
            do {
                posts = api.fetchPostsFromFirstPage(page)
                _allPosts.value = _allPosts.value + posts
                page++
            } while (posts.isNotEmpty())
        } catch (e: Exception) {
            Log.e("ER", e.message.toString())
        }
    }

    suspend fun fetchAllPostsTEST(categoryId: Int) {
        try {
            _allCategoryPosts.value = emptyList()
            var page = 1
            var posts: List<PostsOfUpieczonaItemDto>
            do {
                posts = api.fetchCategoryPosts(categoryId, page)
                _allCategoryPosts.value = _allCategoryPosts.value + posts
                page++
            } while (posts.isNotEmpty())
        } catch (e: Exception) {
            Log.e("ER", e.message.toString())
        }
    }




}