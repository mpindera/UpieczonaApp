package com.example.upieczona.contentview


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.upieczona.destination.Destination
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.UpieczonaViewModel


@Composable
fun ContentViewUpieczona(
    postIndex: Int?, upieczonaViewModel: UpieczonaViewModel, navController: NavHostController
) {
    val postDetails = upieczonaViewModel.allPosts
    val regexx = """<a href=\s*['"]([^'"]*)['"][^>]*>""".toRegex(RegexOption.IGNORE_CASE)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBarUpieczona {
            navController.navigate(Destination.MainPageOfUpieczona.route)
        }

        FetchDetails(upieczonaViewModel = upieczonaViewModel, postIndex = postIndex)
        LazyColumn {
            items(postDetails.value.size) { index ->

                val matches = regexx.findAll(postDetails.value[index].content.rendered)
                val urls = matches.map { it.groupValues[1] }.toList()

                val decodedText = HtmlCompat.fromHtml(
                    postDetails.value[index].title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()

                if (postDetails.value[index].id == postIndex) {

                    Divider()
                    ImagePager(urls.size, urls)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            modifier = Modifier.padding(10.dp),
                            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
                            text = decodedText,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(imageCount: Int, urls: List<String>) {
    val pageState = rememberPagerState(initialPage = 0)

    HorizontalPager(
        pageCount = imageCount,
        state = pageState
    ) { pageIndex ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = urls[pageIndex],
                contentDescription = null,
                modifier = Modifier.background(Color.White)
            )
        }
    }

    Divider()
    Row(
        modifier = Modifier
            .height(20.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(imageCount) { iteration ->
            val color =
                if (pageState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(7.dp)

            )
        }
    }
}

/*@Composable
fun abc() {
    LaunchedEffect(ImagePager(imageCount = , urls = )) {

    }
}*/

@Composable
fun FetchDetails(upieczonaViewModel: UpieczonaViewModel, postIndex: Int?) {
    LaunchedEffect(postIndex) {
        if (postIndex != null) {
            upieczonaViewModel.fetchPostById(postIndex)
        }
    }
}