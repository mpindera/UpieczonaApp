package com.example.upieczona.favorite

import android.util.Log
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
=======
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
<<<<<<< HEAD
import com.example.upieczona.R
=======
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
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
<<<<<<< HEAD

  if (favoritePosts.isEmpty()) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
      Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
          Image(painter = painterResource(id = R.drawable.rotatingimage), contentDescription = null)
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
          Image(painter = painterResource(id = R.drawable.rotatingimage), contentDescription = null)
        }
      }
=======
  Log.d("cacaTest","sec - ${FavoriteManagerSingleton.getMap()}")

  if (favoritePosts.isEmpty()) {
    Column {
      Text(text = "You don't have any favorites")
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
    }
  } else {
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      state = scrollState
    ) {
      items(sortedFavoritePosts.size) { index ->
<<<<<<< HEAD
=======

>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
        val decodedTextPostName = HtmlCompat.fromHtml(
          sortedFavoritePosts[index].value.postName,
          HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()

        Box(
          modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.5f)
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
            Box(
              modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 25.dp)
                .background(Color.White), contentAlignment = Alignment.Center
            ) {
              val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                  .data(data = sortedFavoritePosts[index].value.postImageUrl)
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
              val icon = if (favoriteManager.isPostFavorite(postId = sortedFavoritePosts[index].key)) {
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



