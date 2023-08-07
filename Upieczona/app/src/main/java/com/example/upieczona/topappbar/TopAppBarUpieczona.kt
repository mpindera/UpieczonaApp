package com.example.upieczona.topappbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.upieczona.R
import com.example.upieczona.staticobjects.MaterialsUtils
import kotlinx.coroutines.delay

@Composable
fun TopAppBarUpieczona(
    onUpieczonaClick: () -> Unit
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
        onClickUpieczona = onUpieczonaClick
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    onClickUpieczona: () -> Unit
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
    )
}