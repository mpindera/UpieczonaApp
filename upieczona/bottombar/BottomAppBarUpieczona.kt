package com.example.upieczona.bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.staticobjects.MaterialsUtils

@Composable
fun BottomAppBarUpieczona(navController: NavController) {
    BottomBar(navController)
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(75.dp), actions = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    shape = RoundedCornerShape(100),
                    containerColor = MaterialsUtils.colorPink,
                    onClick = {
                        navController.navigate("FavoritePage")
                    }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialsUtils.colorRed
                    )
                }
            }
        }, containerColor = MaterialsUtils.colorPinkMain
    )
}


@Preview(showBackground = true)
@Composable
fun BottomAppBarUpieczonaPreview() {
    val navController = rememberNavController()
    BottomAppBarUpieczona(navController)
}