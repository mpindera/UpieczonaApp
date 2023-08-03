package com.example.upieczona.mainscreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.upieczona.viewmodels.UpieczonaViewModel
import com.example.upieczona.dto.PostsOfUpieczonaItemDto
import com.example.upieczona.grid.LazyGridOfPosts

@Composable
fun MainPageOfUpieczona(upieczonaViewModel: UpieczonaViewModel) {

    val state2 by upieczonaViewModel.state2.collectAsState()
    val isLoading by upieczonaViewModel.isLoading.collectAsState()
    val allPosts = upieczonaViewModel.allPosts
    val allPostsFromCategories = upieczonaViewModel.allCategoryPosts

    var selectedIndex by remember { mutableStateOf(0) }
    var chosenCategory by remember { mutableStateOf("") }
    var showIndicator by remember { mutableStateOf(true) }
    val post: State<List<PostsOfUpieczonaItemDto>>

    var select by remember {
        mutableStateOf(0)
    }

    val scrollState by remember {
        mutableStateOf(LazyGridState(0))
    }

    fun onTabClick(index: Int) {
        selectedIndex = index
    }

    when (select) {
        0 -> {
            post = allPosts
            FetchAllPosts(upieczonaViewModel = upieczonaViewModel)
        }

        else -> {
            post = allPostsFromCategories
            FetchAllPostsByCategories(upieczonaViewModel = upieczonaViewModel, select = select)
        }
    }

    if (isLoading) {
        CircularView()
    } else {
        if (state2.isEmpty()) {
            Text(text = "No data available")
        } else {
            ScrollableTabRow(
                selectedTabIndex = selectedIndex,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    when (select) {
                        0 -> null
                        else -> IndicatorWhenSelectIsZero(tabPositions, selectedIndex)
                    }
                }
            ) {
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
                            text = category.name.uppercase()
                        )
                    })
                }
            }
            LazyGridOfPosts(allPosts = post, scrollState = scrollState)
        }
    }
}

@Composable
private fun FetchAllPosts(upieczonaViewModel: UpieczonaViewModel) {
    LaunchedEffect(key1 = 1) {
        upieczonaViewModel.fetchAllPosts()
    }
}

@Composable
private fun FetchAllPostsByCategories(upieczonaViewModel: UpieczonaViewModel, select: Int) {
    LaunchedEffect(key1 = select) {
        upieczonaViewModel.fetchAllPostsTEST(select)
    }
}

@Composable
private fun CircularView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun IndicatorWhenSelectIsZero(tabPositions: List<TabPosition>, selectedIndex: Int) {
    TabRowDefaults.Indicator(
        Modifier
            .tabIndicatorOffset(tabPositions[selectedIndex])
            .height(3.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun IndicatorWhenSelect(tabPositions: List<TabPosition>, selectedIndex: Int) {
    TabRowDefaults.Indicator(
        Modifier
            .tabIndicatorOffset(tabPositions[selectedIndex])
            .height(3.dp),
        color = MaterialTheme.colorScheme.primary
    )
}