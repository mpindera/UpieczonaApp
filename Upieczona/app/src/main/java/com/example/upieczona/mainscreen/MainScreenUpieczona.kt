package com.example.upieczona.mainscreen

import android.app.Activity
import androidx.activity.compose.BackHandler

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
<<<<<<< HEAD
fun MainScreenUpieczona(navController: NavHostController,mainViewModel:MainViewModel) {
=======
fun MainScreenUpieczona(navController: NavHostController) {
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
    shadowElevation = 20.dp,
  ) {
    val activity = LocalContext.current as? Activity
<<<<<<< HEAD

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
=======
    var mainPageState by remember {
      mutableStateOf(MainPageState.Default)
    }

    LaunchedEffect(mainPageState) {
      if (mainPageState == MainPageState.UpieczonaClicked) {
        navController.navigate(Destination.MainPageOfUpieczona.route)
      }
    }

    BackHandler(enabled = true) {
      activity?.moveTaskToBack(true)
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
        MainPageOfUpieczonaWithRecipes(
          upieczonaViewModel = ApiUtils.apiUtil,
          mainPageState = mainPageState,
          navController = navController
        ) { newState ->
          mainPageState = newState
        }
      }
    }, bottomBar = {
      BottomAppBarUpieczona(navController = navController)
    })

  }
}

enum class MainPageState {
  Default, UpieczonaClicked
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
}

@Composable
fun MainPageOfUpieczonaWithRecipes(
  upieczonaViewModel: UpieczonaViewModel,
  mainPageState: MainPageState,
  navController: NavHostController,
  onStateChange: (MainPageState) -> Unit
) {
<<<<<<< HEAD
  if (mainPageState == MainPageState.DEFAULT) {
=======
  if (mainPageState == MainPageState.Default) {
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
    MainPageOfUpieczona(
      upieczonaViewModel = upieczonaViewModel,
      navController = navController
    )
  }
}

