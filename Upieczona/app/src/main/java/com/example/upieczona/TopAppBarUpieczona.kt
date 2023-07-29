package com.example.http

import android.os.Looper
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.upieczona.category.CategoryTopTab
import com.example.upieczona.grid.LazyGridOfPosts
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUpieczona(
    upieczonaViewModel: UpieczonaViewModel,
    onUpieczonaClick: () -> Unit
) {
    val context = LocalContext.current

    val state = upieczonaViewModel.FirstStateNames.collectAsState()

    var shineAlpha by remember { mutableStateOf(1f) }

    val tapGesture = Modifier.pointerInput(Unit) {
        detectTapGestures { tapOffset ->
            shineAlpha = 0.5f
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                shineAlpha = 1f
            }, 100)
            onUpieczonaClick()
        }
    }

    LaunchedEffect(Unit) {
        val waitDuration = 100L
        delay(waitDuration)
        shineAlpha = 1f
    }


    TopAppBar(
        title = {
            Text(
                text = "Upieczona",
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .then(tapGesture)
                    .alpha(shineAlpha)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.hsl(350.0F, 0.4F, 0.88F)
        ),
    )


}
