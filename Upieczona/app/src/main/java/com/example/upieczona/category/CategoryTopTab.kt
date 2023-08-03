package com.example.upieczona.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.upieczona.viewmodels.UpieczonaViewModel
import com.example.upieczona.grid.LazyGridOfPosts

@Composable
fun CategoryTopTab(upieczonaViewModel: UpieczonaViewModel, navController: NavController) {

    val state2 by upieczonaViewModel.state2.collectAsState()
    val isLoading by upieczonaViewModel.isLoading.collectAsState()
    var selectedIndex by remember { mutableStateOf(-1) }
    var showIndicator by remember { mutableStateOf(true) }
    var chosenCategory by remember { mutableStateOf("") }
    val allPosts = upieczonaViewModel.allPosts
    val allPostsFromCategories = upieczonaViewModel.allCategoryPosts

    val postsToShow = if (selectedIndex == -1) allPosts else allPostsFromCategories

    var select by remember {
        mutableStateOf(0)
    }

    var scrollState by remember {
        mutableStateOf(LazyGridState(0))
    }

    fun onTabClick(index: Int) {
        selectedIndex = index
    }



    if (selectedIndex == -1) {
        FetchAllPosts(upieczonaViewModel = upieczonaViewModel)
    } else {
        FetchAllPostsByCategories(upieczonaViewModel = upieczonaViewModel, select = select)
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        if (state2.isEmpty()) {
            Text(text = "No data available")
        } else {
            Column {
                ScrollableTabRow(selectedTabIndex = selectedIndex.coerceIn(-1, state2.size - 1),
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
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
                                text = category.name.uppercase()
                            )
                        })
                    }
                }
                LazyGridOfPosts(allPosts = postsToShow, scrollState = scrollState)
            }
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
