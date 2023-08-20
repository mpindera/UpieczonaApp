package com.example.upieczona.grid

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.upieczona.FavoriteManager
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils.CreateBoxContent

@Composable
fun LazyGridOfPosts(
    allPosts: State<List<PostsOfUpieczonaItemDto>>,
    scrollState: LazyGridState,
    navController: NavHostController
) {
    val loc = LocalContext.current
    val favoriteManager = remember { FavoriteManager(loc) }
    val favoritePostsState = remember { mutableStateOf(favoriteManager.getFavoritePosts()) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = scrollState,
        content = {
            items(allPosts.value.size) { index ->
                val decodedTextPostName = HtmlCompat.fromHtml(
                    allPosts.value[index].title.rendered,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
                val isFavorite = favoritePostsState.value.contains(decodedTextPostName)

                val imageUrl =
                    allPosts.value[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
                val twitterImageUrl = allPosts.value[index].yoastHeadJson.twitterImage

                val selectedImageUrl = imageUrl ?: twitterImageUrl

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(0.5f)
                        .clickable {
                            navController.navigate("Content/${allPosts.value[index].id}")
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

                        // First Section Image
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 25.dp)
                                .background(Color.White), contentAlignment = Alignment.Center
                        ) {


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

                        // favorite Section
                        IconButton(
                            onClick = {
                                if (isFavorite) {
                                    favoriteManager.removeFavoritePost(decodedTextPostName)
                                } else {
                                    favoriteManager.addFavoritePost(
                                        decodedTextPostName,
                                        selectedImageUrl
                                    )
                                }
                                favoritePostsState.value = favoriteManager.getFavoritePosts()
                            },
                            modifier = Modifier.align(Alignment.Start)
                        ) {
                            val icon = if (isFavorite) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            }
                            Icon(icon, contentDescription = null)
                        }

                        // Second Section Text
                        Box(
                            modifier = Modifier
                                .weight(0.5f)
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.White), contentAlignment = Alignment.Center
                        ) {

                            Text(
                                fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF000000),
                                text = decodedTextPostName,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun LazyGridOfPostsPreview() {
    val scrollState by remember { mutableStateOf(LazyGridState(0)) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        state = scrollState,
        content = {
            items(1) {
                CreateBoxContent()
            }
        }
    )
}