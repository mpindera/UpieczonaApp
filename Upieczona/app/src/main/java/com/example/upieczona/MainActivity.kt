package com.example.upieczona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.http.NetworkModule
import com.example.http.TopAppBarUpieczona
import com.example.http.UpieczonaViewModel
import com.example.upieczona.ui.theme.UpieczonaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpieczonaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        TopAppBarUpieczona()
                        FetchCategoriesFromApi()
                    }
                }
            }
        }
    }

    @Composable
    private fun FetchDataFromApi() {
        val viewModel = remember {
            UpieczonaViewModel(api = NetworkModule().api)
        }
        val state = viewModel.state.collectAsState()

        LazyRow {
            items(state.value) {
                Text(text = it.title.rendered)
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }

    @Composable
    private fun FetchCategoriesFromApi() {
        val viewModel = remember {
            UpieczonaViewModel(api = NetworkModule().api)
        }

        val state2 = viewModel.state2.collectAsState()

        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 25.dp)
        ) {
            LazyRow {
                items(state2.value) {
                    Text(text = it.name)
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
        }

    }
}