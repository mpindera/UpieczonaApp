package com.example.upieczona.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.upieczona.favorite.FavoriteManager
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils.CreateBoxContent
import com.example.upieczona.staticobjects.MaterialsUtils.decodeHtml
import com.example.upieczona.viewmodels.UpieczonaViewModel

@Composable
fun LazyGridOfPosts(
  allPosts: State<List<PostsOfUpieczonaItemDto>>,
  scrollState: LazyGridState,
  navController: NavHostController,
  upieczonaViewModel: UpieczonaViewModel
) {
  val loc = LocalContext.current
  val favoriteManager = remember { FavoriteManager(loc) }
  val favoritePostsState = remember { mutableStateOf(favoriteManager.getFavoritePosts()) }

  val tagsState = upieczonaViewModel.tagsUpieczona.collectAsState()
  val tagMap = remember(tagsState.value) {
    val map = mutableMapOf<Int, String>()
    tagsState.value.forEach { tag -> map[tag.id] = tag.name }
    map
  }

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    state = scrollState,
    content = {
      items(allPosts.value.size) { index ->
        val decodedTextPostName = allPosts.value[index].title.rendered.decodeHtml()
        val isFavorite = favoritePostsState.value.contains(allPosts.value[index].id)

        val imageUrl = allPosts.value[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
        val twitterImageUrl = allPosts.value[index].yoastHeadJson.twitterImage
        val selectedImageUrl = imageUrl ?: twitterImageUrl

        Box(
          modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.4f)
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

            //Image section
            Box(
              modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding()
                .background(Color.White), contentAlignment = Alignment.Center
            ) {
              val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                  .data(data = selectedImageUrl)
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

            //favorite section
            IconButton(
              onClick = {
                if (isFavorite) {
                  favoriteManager.removeFavoritePost(allPosts.value[index].id)
                } else {
                  favoriteManager.addFavoritePost(
                    postId = allPosts.value[index].id,
                    postName = decodedTextPostName,
                    postImageUrl = selectedImageUrl
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

            //Tag section
            Row {
              val tagNames = allPosts.value[index].tags.mapNotNull { tagId ->
                tagMap[tagId]
              }
              Text(
                text = tagNames.joinToString(", "),
                fontSize = 9.5.sp,
                textAlign = TextAlign.Center
              )
            }

            //Text section 
            Box(
              modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .padding()
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White), contentAlignment = Alignment.Center
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
        }
      }
    }
  )
}


@Preview(showBackground = true)
@Composable
fun LazyGridOfPostsPreview() {
  val scrollState by remember { mutableStateOf(LazyGridState(0)) }
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    state = scrollState,
    content = {
      items(4) {
        CreateBoxContent()
      }
    }
  )
}



/*
package com.example.upieczona.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.upieczona.favorite.FavoriteManager
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils.CreateBoxContent
import com.example.upieczona.staticobjects.MaterialsUtils.decodeHtml
import com.example.upieczona.viewmodels.UpieczonaViewModel

@Composable
fun LazyGridOfPosts(
  allPosts: State<List<PostsOfUpieczonaItemDto>>,
  scrollState: LazyGridState,
  navController: NavHostController,
  upieczonaViewModel: UpieczonaViewModel
) {
  val loc = LocalContext.current
  val favoriteManager = remember { FavoriteManager(loc) }
  val favoritePostsState = remember { mutableStateOf(favoriteManager.getFavoritePosts()) }

  val tagsState = upieczonaViewModel.tagsUpieczona.collectAsState()
  val tagMap = remember(tagsState.value) {
    val map = mutableMapOf<Int, String>()
    tagsState.value.forEach { tag -> map[tag.id] = tag.name }
    map
  }

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    state = scrollState,
    content = {
      items(allPosts.value.size) { index ->
        val decodedTextPostName = allPosts.value[index].title.rendered.decodeHtml()
        val isFavorite = favoritePostsState.value.contains(allPosts.value[index].id)

        val imageUrl = allPosts.value[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
        val twitterImageUrl = allPosts.value[index].yoastHeadJson.twitterImage
        val selectedImageUrl = imageUrl ?: twitterImageUrl

        Box(
          modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.4f)
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

            //Image section
            Box(
              modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding()
                .background(Color.White), contentAlignment = Alignment.Center
            ) {
              val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                  .data(data = selectedImageUrl)
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

            //favorite section
            IconButton(
              onClick = {
                if (isFavorite) {
                  favoriteManager.removeFavoritePost(allPosts.value[index].id)
                } else {
                  favoriteManager.addFavoritePost(
                    postId = allPosts.value[index].id,
                    postName = decodedTextPostName,
                    postImageUrl = selectedImageUrl
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

            //Tag section
            Row {
              val tagNames = allPosts.value[index].tags.mapNotNull { tagId ->
                tagMap[tagId]
              }
              Text(
                text = tagNames.joinToString(", "),
                fontSize = 9.5.sp,
                textAlign = TextAlign.Center
              )
            }

            //Text section
            Box(
              modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .padding()
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White), contentAlignment = Alignment.Center
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


*/