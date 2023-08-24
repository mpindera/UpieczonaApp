package com.example.upieczona.bottombar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.R

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
        Row() {
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
          FloatingActionButton(
            onClick = {

            }) {
            Icon(
              painter = painterResource(id = R.drawable.baseline_filter_alt_24),
              contentDescription = null
            )
          }
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