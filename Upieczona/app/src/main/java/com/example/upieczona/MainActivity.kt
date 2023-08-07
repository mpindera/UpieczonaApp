package com.example.upieczona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.upieczona.contentview.ContentViewUpieczona
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.MainScreenUpieczona
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.ui.theme.UpieczonaTheme

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
    NavHost(navController = navController, startDestination = Destination.HomePage.route) {
        composable(Destination.HomePage.route) { MainScreenUpieczona(navController) }
        composable(
            Destination.ContentPage.route,
            arguments = listOf(navArgument("postIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val postIndex = backStackEntry.arguments?.getInt("postIndex")
            ContentViewUpieczona(
                postIndex = postIndex,
                upieczonaViewModel = ApiUtils.apiUtil,
                navController = navController
            )
        }
        composable(Destination.MainPageOfUpieczona.route) {
            MainScreenUpieczona(navController)
        }
    }
}



