package com.example.upieczona.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.upieczona.R
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.viewmodels.UpieczonaViewModel
import com.example.upieczona.grid.LazyGridOfPosts

@Composable
fun MainPageOfUpieczona(
    upieczonaViewModel: UpieczonaViewModel,
    navController: NavHostController
) {

    val state2 by upieczonaViewModel.state2.collectAsState()
    val isLoading by upieczonaViewModel.isLoading.collectAsState()
    val allPosts = upieczonaViewModel.allPosts
    val allPostsFromCategories = upieczonaViewModel.allCategoryPosts

    var selectedIndex by remember { mutableStateOf(0) }
    var chosenCategory by remember { mutableStateOf("") }
    var showIndicator by remember { mutableStateOf(true) }
    val post: State<List<PostsOfUpieczonaItemDto>>
    var selectValue by remember { mutableStateOf(0) }
    val scrollState by remember { mutableStateOf(LazyGridState(0)) }

    fun onTabClick(index: Int) {
        selectedIndex = index
    }

    LaunchedEffect(upieczonaViewModel.select) {
        upieczonaViewModel.select.collect { value ->
            selectValue = value
        }
    }

    when (selectValue) {
        0 -> {
            post = allPosts
            FetchAllPosts(upieczonaViewModel = upieczonaViewModel)
        }
        else -> {
            post = allPostsFromCategories
            FetchAllPostsByCategories(upieczonaViewModel = upieczonaViewModel, select = selectValue)
        }
    }

    if (isLoading) {
        CircularView()
    } else {
        if (state2.isEmpty()) {
            Text(text = stringResource(id = R.string.No_data))
        } else {
            ScrollableTabRow(
                selectedTabIndex = selectedIndex,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    when (selectValue) {
                        0 -> null
                        else -> upieczonaViewModel.Indicator(
                            tabPositions,
                            selectedIndex
                        )
                    }
                }
            ) {
                state2.forEachIndexed { index, category ->
                    Tab(modifier = Modifier.padding(
                        start = 5.dp, top = 10.dp, end = 5.dp, bottom = 5.dp
                    ), selected = (selectedIndex == index), onClick = {
                        onTabClick(index)
                        showIndicator = true
                        selectValue = category.id
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
            LazyGridOfPosts(
                allPosts = post,
                scrollState = scrollState,
                navController = navController
            )
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
fun CircularView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}
