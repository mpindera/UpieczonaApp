package com.example.upieczona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.upieczona.contentview.ContentViewUpieczona
import com.example.upieczona.destination.Destination
import com.example.upieczona.favorite.FavoritePage
import com.example.upieczona.filter_page.FilterPage
import com.example.upieczona.mainscreen.MainScreenUpieczona
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.ui.theme.UpieczonaTheme
import com.example.upieczona.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContent {
      UpieczonaTheme {
        val navController = rememberNavController()
        NavigationAppHost(navController = navController)
      }
    }
  }
}


@Composable
fun NavigationAppHost(navController: NavHostController) {
  NavHost(
    navController = navController,
    startDestination = Destination.HomePageOfUpieczona.route
  ) {
    composable(Destination.HomePageOfUpieczona.route) {
      MainScreenUpieczona(
        navController,
        mainViewModel = MainViewModel()
      )
    }
    composable(
      Destination.ContentPageOfUpieczona.route,
      arguments = listOf(navArgument("postIndex") { type = NavType.IntType })
    ) { backStackEntry ->
      val postIndex = backStackEntry.arguments?.getInt("postIndex")
      ContentViewUpieczona(
        postIndex = postIndex,
        upieczonaViewModel = ApiUtils.apiUtil,
        navController = navController,
        mainViewModel = MainViewModel()
      )
    }
    composable(Destination.MainPageOfUpieczona.route) {
      MainScreenUpieczona(navController, mainViewModel = MainViewModel())
    }
    composable(Destination.FavoritePageOfUpieczona.route) {
      FavoritePage(navController = navController, mainViewModel = MainViewModel())
    }
    composable(
      Destination.FilterPageOfUpieczona.route,
      arguments = listOf(navArgument("tagItems") { type = NavType.StringType })
    ) { back ->
      val map = back.arguments?.getString("tagItems")
      FilterPage(
        navController = navController,
        dataMap = map,
        upieczonaViewModel = ApiUtils.apiUtil,
        mainViewModel = MainViewModel()
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
  val navController = rememberNavController()
  NavigationAppHost(navController = navController)
}



