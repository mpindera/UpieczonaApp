package com.example.upieczona.mainscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upieczona.ApiUtils
import com.example.upieczona.bottombar.BottomAppBarUpieczona
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.ViewModelUpieczonaTopAndBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenUpieczona(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 20.dp,
    ) {
        Scaffold(
            topBar = {
                TopAppBarUpieczona(navController)
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                ) {
                    MainPageOfUpieczona(ApiUtils.apiUtil)
                }
            },
            bottomBar = {
                BottomAppBarUpieczona()
            }
        )
    }
}

