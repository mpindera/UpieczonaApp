package com.example.upieczona.contentview

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.CircularView
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.UpieczonaViewModel


@Composable
fun ContentViewUpieczona(
    postIndex: Int?, upieczonaViewModel: UpieczonaViewModel, navController: NavHostController
) {
    val postDetails = upieczonaViewModel.allPosts
    val regexx = """<a href=\s*['"]([^'"]*)['"][^>]*>""".toRegex(RegexOption.IGNORE_CASE)

    val urlsList = postDetails.value.map { post ->
        val matches = regexx.findAll(post.content.rendered)
        matches.map { it.groupValues[1] }.toList()
    }
    val decodedTextList = postDetails.value.map { post ->
        HtmlCompat.fromHtml(
            post.title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
    }

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

                if (postDetails.value[index].id == postIndex) {

                    Divider()
                    ImagePager(urlsList[index].size, urlsList[index])

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
                            text = decodedTextList[index],
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
fun ImagePager(imageUrlsSize: Int, imageUrls: List<String>) {
    val pageState = rememberPagerState()

    HorizontalPager(
        pageCount = imageUrls.size,
        state = pageState
    ) { pageIndex ->
        val imageUrl = imageUrls[0]
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
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
        repeat(imageUrlsSize) { iteration ->
            val color =
                if (pageState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color = color)
                    .size(7.dp)
            )
        }
    }

}

@Composable
fun FetchDetails(upieczonaViewModel: UpieczonaViewModel, postIndex: Int?) {
    LaunchedEffect(postIndex) {
        if (postIndex != null) {
            upieczonaViewModel.fetchPostById(postIndex)
        }
    }
}