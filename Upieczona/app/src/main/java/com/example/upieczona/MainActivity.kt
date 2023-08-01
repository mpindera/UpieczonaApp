package com.example.upieczona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.http.NetworkModule
import com.example.upieczona.category.CategoryTopTab
import com.example.upieczona.contentview.ContentView
import com.example.upieczona.mainscreen.MainScreenUpieczona
import com.example.upieczona.ui.theme.UpieczonaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpieczonaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    shadowElevation = 20.dp
                ) {
                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Destination.HomePage.route) {
        composable(Destination.HomePage.route) { MainScreenUpieczona(navController) }
        composable(
            route = "${Destination.ContentPage}/{chosenItemId}",
            arguments = listOf(navArgument("chosenItemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val chosenItemId = backStackEntry.arguments?.getInt("chosenItemId")
            chosenItemId?.let { chosenId ->
                ContentView(navController, chosenItemId = chosenId)
            }
        }
    }

}
