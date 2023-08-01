package com.example.upieczona.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.http.NetworkModule
import com.example.upieczona.UpieczonaViewModel
import com.example.upieczona.category.CategoryTopTab

@Composable
fun MainScreenUpieczona(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CategoryTopTab(
            UpieczonaViewModel(api = NetworkModule().api), navController
        )
        Divider()
    }
}