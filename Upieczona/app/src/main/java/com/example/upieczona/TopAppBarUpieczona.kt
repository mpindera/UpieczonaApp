package com.example.http

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUpieczona() {

    TopAppBar(
        title = {
            Text(
                text = "Upieczona",
                letterSpacing = 3.sp,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.hsl(350.0F, 0.4F, 0.88F)
        ),
    )
}