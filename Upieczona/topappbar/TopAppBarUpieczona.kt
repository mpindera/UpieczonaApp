package com.example.upieczona.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.NavigationAppHost
import com.example.upieczona.R
import com.example.upieczona.staticobjects.MaterialsUtils
import kotlinx.coroutines.delay

@Composable
fun TopAppBarUpieczona(
    onUpieczonaClick: () -> Unit,
    navController: NavHostController
) {
    var shineAlpha by remember {
        mutableStateOf(1f)
    }
    LaunchedEffect(Unit) {
        val waitDuration = 100L
        delay(waitDuration)
        shineAlpha = 1f
    }

    TopBar(
        onClickUpieczona = onUpieczonaClick,
        navController = navController
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    onClickUpieczona: () -> Unit,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .clickable {
                        onClickUpieczona()
                    }
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialsUtils.colorPinkMain
        ),
        navigationIcon = {
            IconButton(onClick = {
                if (navController.currentBackStackEntry?.destination?.route != "MainScreenOfUpieczonaToRefresh") {
                    navController.navigateUp()
                }
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarUpieczonaPreview() {
    val navController = rememberNavController()
    TopBar(onClickUpieczona = {}, navController = navController)
    Divider(modifier = Modifier.height(2.dp).background(Color.Black))

}