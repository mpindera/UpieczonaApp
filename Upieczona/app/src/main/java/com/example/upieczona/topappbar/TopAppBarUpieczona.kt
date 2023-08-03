package com.example.upieczona.topappbar

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upieczona.Destination
import com.example.upieczona.staticver.MaterialsUtils
import com.example.upieczona.viewmodels.ViewModelUpieczonaTopAndBottomBar
import kotlinx.coroutines.delay

// Inside your TopAppBarUpieczona composable
@Composable
fun TopAppBarUpieczona(navController: NavHostController) {

    val upieczonaViewModel: ViewModelUpieczonaTopAndBottomBar = viewModel()


    LaunchedEffect(Unit) {
        val waitDuration = 100L
        delay(waitDuration)
        upieczonaViewModel.shineAlpha = 1f
    }

    TopBar(upieczonaViewModel = upieczonaViewModel, navController = navController)
}

// Inside your TopBar composable
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    upieczonaViewModel: ViewModelUpieczonaTopAndBottomBar,
    navController: NavHostController,
) {
    val materialTypo = MaterialTheme.typography.titleLarge.fontFamily
    var info = true
    Log.d("VAV", info.toString())
    TopAppBar(
        title = {
            Text(
                text = "Upieczona",
                fontFamily = materialTypo,
                modifier = Modifier
                    .fillMaxSize()
                    .then(upieczonaViewModel.tapGesture)
                    .clickable {
                        if (info) {
                            navController.navigate("MainScreenOfUpieczonaToRefresh")
                            info = false
                        }
                    }
                    .wrapContentSize(Alignment.Center)
                    .alpha(upieczonaViewModel.shineAlpha)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialsUtils.colorPinkMain
        ),
    )
}
