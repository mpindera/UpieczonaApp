package com.example.upieczona.grid

import android.content.res.Resources
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.http.NetworkModule
import com.example.http.UpieczonaViewModel
import com.example.upieczona.R

@Composable
fun LazyGridOfPosts(upieczonaViewModel: UpieczonaViewModel, selectedCategoryIndex: Int) {
    val state = upieczonaViewModel.stateNames.collectAsState()
    val context = LocalContext.current
    Text(text = selectedCategoryIndex.toString())
/*    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(state.size) { index ->

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(0.5f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            Toast
                                .makeText(context, "$index", Toast.LENGTH_SHORT)
                                .show()
                        },
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
                        val imageUrl = state[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
                        val twitterImageUrl = state[index].yoastHeadJson.twitterImage

                        val selectedImageUrl = imageUrl ?: twitterImageUrl

                        val painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = selectedImageUrl)
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
                            state[index].title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString()
                        Text(
                            textAlign = TextAlign.Center,
                            color = Color(0xFF000000), text = decodedText, fontSize = 14.sp
                        )
                    }
                }
            }
        }
    })*/
}

/*items(10) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(0.5f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            Toast
                                .makeText(context, "$1", Toast.LENGTH_SHORT)
                                .show()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    // First Section
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 25.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img71501),
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
                        Text(
                            color = Color(0xFF000000),
                            text = "Wegańskie placuszki jogurtowo-bananowe",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }*/