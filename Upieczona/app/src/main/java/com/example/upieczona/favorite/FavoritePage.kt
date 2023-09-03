package com.example.upieczona.favorite

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePage(navController: NavHostController, mainViewModel: MainViewModel) {
  val local = LocalContext.current
  val favoriteManager = FavoriteManager(local)
  val favoritePosts = favoriteManager.getFavoritePosts()

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
    shadowElevation = 20.dp,
  ) {

    mainViewModel.updatePageState(MainPageState.FAVORITE)
    Scaffold(topBar = {
      TopAppBarUpieczona(
        onUpieczonaClick = {
          navController.navigate(Destination.MainPageOfUpieczona.route)
        },
        onSearchIconClick = {},
        navController = navController,
        pageInfo = mainViewModel.pageState.value,
        mainViewModel = MainViewModel(),
      )
    }, content = { padding ->
      Column(
        modifier = Modifier.padding(padding)
      ) {
        FavoriteGrid(favoritePosts, navController, ApiUtils.apiUtil)
      }
    })
  }
}