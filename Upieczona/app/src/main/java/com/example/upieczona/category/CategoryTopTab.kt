package com.example.upieczona.category

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.http.TopAppBarUpieczona
import com.example.http.UpieczonaViewModel
import com.example.upieczona.grid.LazyGridOfPosts

@Composable
fun CategoryTopTab(upieczonaViewModel: UpieczonaViewModel) {

    val state2 by upieczonaViewModel.state2.collectAsState()
    val isLoading by upieczonaViewModel.isLoading.collectAsState()
    var selectedIndex by remember { mutableStateOf(-1) }
    var showIndicator by remember { mutableStateOf(true) }
    val state = upieczonaViewModel.stateNames.collectAsState()
    val stateNames = upieczonaViewModel.FirstStateNames.collectAsState()
    var chosenCategory by remember { mutableStateOf("") }

    var select by remember {
        mutableStateOf(0)
    }

    fun onTabClick(index: Int) {
        selectedIndex = index
    }

    TopAppBarUpieczona(upieczonaViewModel) {
        selectedIndex = -1
    }
    Divider()

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
                                        0, state2.size - 1
                                    )]
                                )
                            )
                        }
                    }) {
                    state2.forEachIndexed { index, category ->
                        Tab(modifier = Modifier.padding(
                            start = 5.dp, top = 10.dp, end = 5.dp, bottom = 5.dp
                        ), selected = (selectedIndex == index), onClick = {
                            onTabClick(index)
                            showIndicator = true
                            select = category.id
                            chosenCategory = category.name

                        }, text = {
                            Text(
                                fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                                color = Color(0xFF001018),
                                fontStyle = FontStyle.,
                                text = category.name
                            )
                        })
                    }
                }

                LaunchedEffect(select) {
                    upieczonaViewModel.fetchPostsForCategory(select)
                }
//TODO
                val postsToShow = if (selectedIndex == -1) stateNames else state
                Text(
                    fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                    color = Color(0xFF001018),
                    text = chosenCategory
                )
                LazyGridOfPosts(posts = postsToShow)

            }
        }
    }
}