package com.example.http

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.http.dto2.CategoriesOfUpieczonaItemDto
import com.example.upieczona.dto.PostsOfUpieczonaItemDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale.Category

class UpieczonaViewModel(
    private val api: UpieczonaApi,
) : ViewModel() {

    val state2 = MutableStateFlow(emptyList<CategoriesOfUpieczonaItemDto>())

    val stateNames = MutableStateFlow(emptyList<PostsOfUpieczonaItemDto>())

    val FirstStateNames = MutableStateFlow(emptyList<PostsOfUpieczonaItemDto>())


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


                FirstStateNames.value = responseNames
                state2.value = response

            } catch (e: Exception) {
                Log.e("UpieczonaViewModel", "Error fetching data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun fetchPostsForCategory(categoryId: Int): List<PostsOfUpieczonaItemDto> {
        try {
            val response = api.fetchCategoryPosts(categoryId)
            stateNames.value = response
            return response
        } catch (e: Exception) {
            Log.e("ER", "ERR")
        }
        return emptyList()
    }
}