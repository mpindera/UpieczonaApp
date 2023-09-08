package com.example.upieczona.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.upieczona.R
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.viewmodels.UpieczonaViewModel

@Composable
fun FavoriteGrid(
  favoritePosts: Map<Int, FavoriteData>,
  navController: NavHostController,
  upieczonaViewModel: UpieczonaViewModel
) {
  val loc = LocalContext.current
  val favoriteManager = remember { FavoriteManager(loc) }
  val favoritePostsState = remember { mutableStateOf(favoriteManager.getFavoritePosts()) }
  val scrollState by remember { mutableStateOf(LazyGridState(0)) }
  val allPosts: State<List<PostsOfUpieczonaItemDto>> = upieczonaViewModel.allPosts
  val sortedFavoritePosts = favoritePostsState.value.entries.sortedBy { it.key }

  if (favoritePosts.isEmpty()) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(50.dp)
    ) {
      Box(modifier = Modifier
        .padding(start = 30.dp, end = 30.dp)
        .fillMaxSize()) {
        Box(modifier = Modifier
          .size(80.dp)
          .align(Alignment.CenterStart)) {
          Image(painter = painterResource(id = R.drawable.rotatingimage), contentDescription = null)
        }
        Box(modifier = Modifier
          .size(80.dp)
          .align(Alignment.CenterEnd)) {
          Image(painter = painterResource(id = R.drawable.rotatingimage), contentDescription = null)
        }
      }
    }
  } else {
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      state = scrollState
    ) {
      items(sortedFavoritePosts.size) { index ->
        val decodedTextPostName = HtmlCompat.fromHtml(
          sortedFavoritePosts[index].value.postName,
          HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()

        Box(
          modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.4f)
            .clickable {
              val post =
                allPosts.value.find { it.id == sortedFavoritePosts[index].key }

              if (post != null) {
                val postIndex = allPosts.value.indexOf(post)
                if (postIndex >= 0) {
                  handleItemClick(
                    navController,
                    postIndex,
                    upieczonaViewModel
                  )
                }
              }
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
            PhotoSection(sortedFavoritePosts,index)

            // favorite Section
            IconButton(
              onClick = {
                if (favoriteManager.isPostFavorite(postId = sortedFavoritePosts[index].key)) {
                  favoriteManager.removeFavoritePost(sortedFavoritePosts[index].key)
                } else {
                  favoriteManager.addFavoritePost(
                    postId = sortedFavoritePosts[index].key,
                    postName = sortedFavoritePosts[index].value.postName,
                    postImageUrl = sortedFavoritePosts[index].value.postImageUrl
                  )
                }
                favoritePostsState.value = favoriteManager.getFavoritePosts()
              },
              modifier = Modifier.align(Alignment.Start)
            ) {
              val icon =
                if (favoriteManager.isPostFavorite(postId = sortedFavoritePosts[index].key)) {
                  Icons.Default.Favorite
                } else {
                  Icons.Default.FavoriteBorder
                }
              Icon(icon, contentDescription = null)
            }

            // Second Section Text
            TextSection(decodedTextPostName)
          }
        }
      }
    }
  }
}

fun handleItemClick(
  navController: NavHostController,
  postIndex: Int,
  upieczonaViewModel: UpieczonaViewModel
) {
  val allPosts: State<List<PostsOfUpieczonaItemDto>> = upieczonaViewModel.allPosts
  navController.navigate("Content/${allPosts.value[postIndex].id}")
}

@Composable
fun PhotoSection(sortedFavoritePosts: List<Map.Entry<Int, FavoriteData>>, index: Int) {
  Box(
    modifier = Modifier
      .height(250.dp)
      .fillMaxWidth()
      .padding()
      .background(Color.White), contentAlignment = Alignment.Center
  ) {
    val painter = rememberAsyncImagePainter(
      ImageRequest.Builder(LocalContext.current)
        .data(data = sortedFavoritePosts[index].value.postImageUrl)
        .apply {
          crossfade(true)
          memoryCachePolicy(CachePolicy.ENABLED)
          diskCachePolicy(CachePolicy.ENABLED)
        }
        .build()
    )
    Image(
      painter = painter,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize()
    )
  }
}

@Composable
fun TextSection(decodedTextPostName: String) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 10.dp, end = 10.dp)
      .clip(RoundedCornerShape(5.dp))
      .background(Color.White)
      .sizeIn(maxHeight = 80.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
      textAlign = TextAlign.Center,
      color = Color(0xFF000000),
      text = decodedTextPostName,
      fontSize = 13.sp
    )
  }
}



