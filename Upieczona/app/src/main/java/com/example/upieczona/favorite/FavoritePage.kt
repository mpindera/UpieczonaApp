package com.example.upieczona.favorite

<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
<<<<<<< HEAD
=======
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.topappbar.TopAppBarUpieczona
<<<<<<< HEAD
import com.example.upieczona.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePage(navController: NavHostController, mainViewModel: MainViewModel) {
=======

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePage(navController: NavHostController) {
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
  val local = LocalContext.current
  val favoriteManager = FavoriteManager(local)
  val favoritePosts = favoriteManager.getFavoritePosts()

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
    shadowElevation = 20.dp,
  ) {
<<<<<<< HEAD

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
=======
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
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
    }, content = { padding ->
      Column(
        modifier = Modifier.padding(padding)
      ) {
        FavoriteGrid(favoritePosts, navController, ApiUtils.apiUtil)
      }
    })
  }
}