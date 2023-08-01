package com.example.upieczona.grid

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.upieczona.Destination
import com.example.upieczona.dto.PostsOfUpieczonaItemDto

@Composable
fun LazyGridOfPosts(
    allPosts: State<List<PostsOfUpieczonaItemDto>>,
    scrollState: LazyGridState,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = scrollState,
        content = {
            items(allPosts.value.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(0.5f)
                        .clickable {
                            navController.navigate(Destination.ContentPage.route)
                        }
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        // First Section
                        Box(
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 25.dp)
                                .background(Color.White), contentAlignment = Alignment.Center
                        ) {
                            val imageUrl =
                                allPosts.value[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
                            val twitterImageUrl = allPosts.value[index].yoastHeadJson.twitterImage

                            val selectedImageUrl = imageUrl ?: twitterImageUrl

                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = selectedImageUrl)
                                    .apply {
                                        crossfade(true)
                                    }.build()
                            )
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Second Section
                        Box(
                            modifier = Modifier
                                .weight(0.5f)
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.White), contentAlignment = Alignment.Center
                        ) {
                            val decodedText = HtmlCompat.fromHtml(
                                allPosts.value[index].title.rendered,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toString()
                            Text(
                                fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF000000), text = decodedText, fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    )
}
