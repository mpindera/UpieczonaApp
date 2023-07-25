package com.example.http

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.http.dto.DTOItemDto
import com.example.http.dto2.CategoriesOfUpieczonaItemDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UpieczonaViewModel(
    private val api: UpieczonaApi,
) : ViewModel() {

    val state = MutableStateFlow(emptyList<DTOItemDto>())
    val state2 = MutableStateFlow(emptyList<CategoriesOfUpieczonaItemDto>())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.fetchAll()

            state2.value = response
            Log.d("RESPONSEEEEEE","$response")
        }
    }
}