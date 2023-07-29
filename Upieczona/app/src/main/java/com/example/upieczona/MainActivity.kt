package com.example.upieczona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.http.NetworkModule
import com.example.http.TopAppBarUpieczona
import com.example.http.UpieczonaViewModel
import com.example.upieczona.category.CategoryTopTab
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CategoryTopTab(
                            UpieczonaViewModel(api = NetworkModule().api)
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

