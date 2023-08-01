package com.example.upieczona.contentview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.http.NetworkModule
import com.example.upieczona.UpieczonaViewModel
import com.example.upieczona.category.CategoryTopTab

@Composable
fun ContentView(navController: NavHostController, chosenItemId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "HELLoo")

        Divider()
    }
}
