package com.example.upieczona.grid

<<<<<<< HEAD
import android.graphics.Bitmap
=======
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
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
<<<<<<< HEAD
import androidx.compose.material3.*
import androidx.compose.runtime.*
=======
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
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
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
<<<<<<< HEAD
import coil.size.OriginalSize
import coil.size.Scale
import coil.size.Size
import com.example.upieczona.favorite.FavoriteManager
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils.CreateBoxContent
import com.example.upieczona.viewmodels.UpieczonaViewModel
=======
import com.example.upieczona.favorite.FavoriteManager
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils.CreateBoxContent
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0

@Composable
fun LazyGridOfPosts(
  allPosts: State<List<PostsOfUpieczonaItemDto>>,
  scrollState: LazyGridState,
<<<<<<< HEAD
  navController: NavHostController,
  upieczonaViewModel: UpieczonaViewModel
=======
  navController: NavHostController
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
) {
  val loc = LocalContext.current
  val favoriteManager = remember { FavoriteManager(loc) }
  val favoritePostsState = remember { mutableStateOf(favoriteManager.getFavoritePosts()) }

<<<<<<< HEAD
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
        val decodedText = HtmlCompat.fromHtml(
          allPosts.value[index].title.rendered,
          HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
        val isFavorite = favoritePostsState.value.contains(allPosts.value[index].id)

        val imageUrl =
          allPosts.value[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
        val twitterImageUrl = allPosts.value[index].yoastHeadJson.twitterImage
        val selectedImageUrl = imageUrl ?: twitterImageUrl

        Box(
          modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.4f)
=======
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    state = scrollState,
    content = {
      items(allPosts.value.size) { index ->
        val decodedTextPostName = HtmlCompat.fromHtml(
          allPosts.value[index].title.rendered,
          HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
        val isFavorite = favoritePostsState.value.contains(allPosts.value[index].id)

        val imageUrl =
          allPosts.value[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
        val twitterImageUrl = allPosts.value[index].yoastHeadJson.twitterImage
        val selectedImageUrl = imageUrl ?: twitterImageUrl

        Box(
          modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.5f)
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
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

<<<<<<< HEAD
            //Image section
            Box(
              modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding()
                .background(Color.White), contentAlignment = Alignment.Center
            ) {
=======
            // First Section Image
            Box(
              modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 25.dp)
                .background(Color.White), contentAlignment = Alignment.Center
            ) {

>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
              val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                  .data(data = selectedImageUrl)
                  .apply {
                    crossfade(true)
<<<<<<< HEAD
                    memoryCachePolicy(CachePolicy.ENABLED)
                    diskCachePolicy(CachePolicy.ENABLED)
                  }
                  .build()
=======
                  }.build()
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
              )
              Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }

<<<<<<< HEAD
            //favorite section
=======
            // favorite Section
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
            IconButton(
              onClick = {
                if (isFavorite) {
                  favoriteManager.removeFavoritePost(allPosts.value[index].id)
                } else {
                  favoriteManager.addFavoritePost(
                    postId = allPosts.value[index].id,
<<<<<<< HEAD
                    postName = decodedText,
=======
                    postName = decodedTextPostName,
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
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

<<<<<<< HEAD
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
=======
            // Second Section Text
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
            Box(
              modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
<<<<<<< HEAD
                .padding()
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White), contentAlignment = Alignment.Center
            ) {
=======
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White), contentAlignment = Alignment.Center
            ) {

>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
              Text(
                fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                textAlign = TextAlign.Center,
                color = Color(0xFF000000),
<<<<<<< HEAD
                text = decodedText,
                fontSize = 13.sp
=======
                text = decodedTextPostName,
                fontSize = 14.sp
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
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