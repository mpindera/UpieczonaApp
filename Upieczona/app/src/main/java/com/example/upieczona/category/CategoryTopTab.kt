package com.example.upieczona.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.http.NetworkModule
import com.example.http.UpieczonaViewModel

@Composable
fun CategoryTopTab() {
    val viewModel = remember {
        UpieczonaViewModel(api = NetworkModule().api)
    }

    var selectedIndex by remember {
        mutableStateOf(0)
    }

    val state2 = viewModel.state2.collectAsState()

    fun onTabClick(index: Int) {
        selectedIndex = index
    }
    if (state2.value.isNotEmpty()) {
        ScrollableTabRow(selectedTabIndex = selectedIndex, edgePadding = 0.dp) {
            state2.value.forEachIndexed { index, category ->
                Tab(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 20.dp,
                        end = 15.dp,
                        bottom = 15.dp
                    ),
                    selected = (selectedIndex == index),
                    onClick = { onTabClick(index) })
                {
                    Text(text = category.name)
                }
            }
        }
    } else {
        Thread.sleep(500)
    }
}