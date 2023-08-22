package com.example.upieczona.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.topappbar.TopAppBarUpieczona

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePage(navController: NavHostController) {
  val local = LocalContext.current
  val favoriteManager = FavoriteManager(local)
  val favoritePosts = favoriteManager.getFavoritePosts()

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
    shadowElevation = 20.dp,
  ) {
    var mainPageState by remember {
      mutableStateOf(MainPageState.Default)
    }

    LaunchedEffect(mainPageState) {
      if (mainPageState == MainPageState.UpieczonaClicked) {
        navController.navigate(Destination.MainPageOfUpieczona.route)
      }
    }

    Scaffold(topBar = {
      if (mainPageState == MainPageState.Default) {
        TopAppBarUpieczona(onUpieczonaClick = {
          mainPageState = MainPageState.UpieczonaClicked
        }, navController = navController)
      }
    }, content = { padding ->
      Column(
        modifier = Modifier.padding(padding)
      ) {
        FavoriteGrid(favoritePosts, navController, ApiUtils.apiUtil)
      }
    })
  }
}