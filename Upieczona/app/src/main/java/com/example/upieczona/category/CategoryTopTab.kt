package com.example.upieczona.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.http.NetworkModule
import com.example.http.UpieczonaViewModel
import com.example.upieczona.grid.LazyGridOfPosts

@Composable
fun CategoryTopTab(upieczonaViewModel: UpieczonaViewModel) {

    val state2 by upieczonaViewModel.state2.collectAsState()
    val isLoading by upieczonaViewModel.isLoading.collectAsState()

    var selectedIndex by remember { mutableStateOf(-1) }
    val showIndicator by remember { mutableStateOf(true) }

    val context = LocalContext.current

    var select by remember {
        mutableStateOf(0)
    }

    fun onTabClick(index: Int) {
        selectedIndex = index
    }

    LaunchedEffect(Unit) {

    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        if (state2.isEmpty()) {
            Text(text = "No data available")
        } else {
            Column() {
                ScrollableTabRow(selectedTabIndex = selectedIndex.coerceIn(-1, state2.size - 1),
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        if (showIndicator && selectedIndex >= 0) {
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(
                                    tabPositions[selectedIndex.coerceIn(
                                        0,
                                        state2.size - 1
                                    )]
                                )
                            )
                        }
                    },
                    divider = {}
                ) {
                    state2.forEachIndexed { index, category ->
                        Tab(
                            modifier = Modifier.padding(
                                start = 5.dp,
                                top = 10.dp,
                                end = 5.dp,
                                bottom = 5.dp
                            ),
                            selected = (selectedIndex == index),
                            onClick = {
                                onTabClick(index)
                                //showIndicator = //TODO Jeżeli kliknę w Upieczona napis
                                select = category.id
                            },
                            text = {
                                Text(color = Color(0xFF001018), text = category.name)
                            }
                        )
                    }

                }
                LazyGridOfPosts(
                    upieczonaViewModel,
                    selectedCategoryIndex = select
                )
            }
        }
    }
}