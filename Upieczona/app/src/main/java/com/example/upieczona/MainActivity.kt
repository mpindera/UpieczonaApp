package com.example.upieczona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.contentview.ContentView
import com.example.upieczona.mainscreen.MainScreenUpieczona
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
        composable(Destination.ContentPage.route) {
            ContentView(navController, chosenItemId = 0)
        }
        composable(Destination.MainPageOfUpieczona.route) {
            MainScreenUpieczona(navController)
        }
    }
}


