package com.example.upieczona.mainscreen

import android.app.Activity
import androidx.activity.compose.BackHandler

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.bottombar.BottomAppBarUpieczona
import com.example.upieczona.destination.Destination
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.MainViewModel
import com.example.upieczona.viewmodels.UpieczonaViewModel

enum class MainPageState {
  DEFAULT, UPIECZONA_CLICKED, FAVORITE, FILTER_PAGE, CONTENT_VIEW
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenUpieczona(navController: NavHostController,mainViewModel:MainViewModel) {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
    shadowElevation = 20.dp,
  ) {
    val activity = LocalContext.current as? Activity

    var mainPageState by remember {
      mutableStateOf(MainPageState.DEFAULT)
    }

    LaunchedEffect(mainPageState) {
      if (mainPageState == MainPageState.UPIECZONA_CLICKED) {
        navController.navigate(Destination.MainPageOfUpieczona.route)
      }
    }

    BackHandler(enabled = true) {
      if (mainPageState == MainPageState.UPIECZONA_CLICKED) {
        mainPageState = MainPageState.DEFAULT
      } else {
        activity?.moveTaskToBack(true)
      }
    }

    mainViewModel.updatePageState(MainPageState.UPIECZONA_CLICKED)

    Scaffold(topBar = {
      TopAppBarUpieczona(
        onUpieczonaClick = {
          mainPageState = MainPageState.UPIECZONA_CLICKED
        },
        onSearchIconClick = {},
        navController = navController,
        pageInfo = mainViewModel.pageState.value,
        mainViewModel = MainViewModel()
      )
    }, content = { padding ->
      Column(
        modifier = Modifier.padding(padding)
      ) {
        MainPageOfUpieczonaWithRecipes(
          upieczonaViewModel = ApiUtils.apiUtil,
          mainPageState = mainPageState,
          navController = navController
        ) { newState ->
          mainPageState = newState
        }
      }
    }, bottomBar = {
      BottomAppBarUpieczona(navController = navController, upieczonaViewModel = ApiUtils.apiUtil)
    })
  }
}

@Composable
fun MainPageOfUpieczonaWithRecipes(
  upieczonaViewModel: UpieczonaViewModel,
  mainPageState: MainPageState,
  navController: NavHostController,
  onStateChange: (MainPageState) -> Unit
) {
  if (mainPageState == MainPageState.DEFAULT) {
    MainPageOfUpieczona(
      upieczonaViewModel = upieczonaViewModel,
      navController = navController
    )
  }
}

