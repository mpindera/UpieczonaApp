package com.example.upieczona

import com.example.upieczona.dtocategories.CategoriesOfUpieczonaItemDto

sealed class CategoriesState {
    object Loading : CategoriesState()
    data class Success(val categories: List<CategoriesOfUpieczonaItemDto>) : CategoriesState()
    data class Error(val message: String) : CategoriesState()
}
