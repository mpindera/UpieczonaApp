package com.example.upieczona.contentview


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun ContentView(navController: NavController, chosenItemId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "HELLoo")
        Divider()
    }
}
