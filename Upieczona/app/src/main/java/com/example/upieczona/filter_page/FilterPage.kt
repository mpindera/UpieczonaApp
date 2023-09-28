package com.example.upieczona.filter_page

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.upieczona.bottombar.BottomAppBarUpieczona
import com.example.upieczona.destination.Destination
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.favorite.FavoriteManager
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.staticobjects.MaterialsUtils.decodeHtml
import com.example.upieczona.staticobjects.MaterialsUtils.swipeToReturn
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.UpieczonaMainViewModel
import com.example.upieczona.viewmodels.UpieczonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterPage(
  navController: NavHostController,
  dataMap: String?,
  upieczonaApiViewModel: UpieczonaViewModel,
  upieczonaMainViewModel: UpieczonaMainViewModel
) {
  val scrollState by remember { mutableStateOf(LazyGridState(0)) }
  val isSwipedRight by remember { mutableStateOf(false) }

  upieczonaMainViewModel.updatePageState(MainPageState.FILTER_PAGE)

  Scaffold(
    topBar = {
      TopAppBarUpieczona(
        onUpieczonaClick = {
          navController.navigate(Destination.MainPageOfUpieczona.route)
        },
        onSearchIconClick = {},
        navController = navController,
        pageInfo = upieczonaMainViewModel.pageState.value,
        upieczonaMainViewModel = UpieczonaMainViewModel()
      )
    }, content = { padding ->
      Column(
        modifier = Modifier.padding(padding).swipeToReturn(isSwipedRight = isSwipedRight, navController = navController)
      ) {
        FetchDataFromFilter(
          scrollState = scrollState,
          navController = navController,
          filterMap = dataMap,
          upieczonaApiViewModel = upieczonaApiViewModel,
          upieczonaMainViewModel= upieczonaMainViewModel
        )
      }
    }, bottomBar = {
      BottomAppBarUpieczona(navController = navController, upieczonaApiViewModel = ApiUtils.apiUtil, upieczonaMainViewModel = UpieczonaMainViewModel())
    })
}

@Composable
fun FetchDataFromFilter(
  scrollState: LazyGridState,
  navController: NavHostController,
  filterMap: String?,
  upieczonaApiViewModel: UpieczonaViewModel,
  upieczonaMainViewModel: UpieczonaMainViewModel,
) {
  val loc = LocalContext.current
  val favoriteManager = remember { FavoriteManager(loc) }
  val favoritePostsState = remember { mutableStateOf(favoriteManager.getFavoritePosts()) }
  val allPosts: State<List<PostsOfUpieczonaItemDto>> = upieczonaApiViewModel.allPosts

  LazyVerticalGrid(columns = GridCells.Fixed(2), state = scrollState, content = {
    val filterTags: List<Int> =
      filterMap?.split(", ")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
    val filteredPosts = upieczonaMainViewModel.searchItemsFromTags(allPosts.value, filterTags)


    items(filteredPosts.size) { index ->
      val decodedTextPostName = filteredPosts[index].title.rendered.decodeHtml()
      val isFavorite = favoritePostsState.value.contains(filteredPosts[index].id)

      val imageUrl = filteredPosts[index].yoastHeadJson.ogImage?.getOrNull(0)?.url
      val twitterImageUrl = filteredPosts[index].yoastHeadJson.twitterImage
      val selectedImageUrl = imageUrl ?: twitterImageUrl

      //View
      Box(modifier = Modifier
        .padding(8.dp)
        .aspectRatio(0.5f)
        .clickable {
          navController.navigate("Content/${filteredPosts[index].id}")
        }
        .background(Color.White), contentAlignment = Alignment.Center) {
        Column(
          modifier = Modifier.fillMaxSize(),
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
              ImageRequest.Builder(LocalContext.current).data(data = selectedImageUrl).apply {
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
                favoriteManager.removeFavoritePost(filteredPosts[index].id)
              } else {
                favoriteManager.addFavoritePost(
                  postId = filteredPosts[index].id,
                  postName = decodedTextPostName,
                  postImageUrl = selectedImageUrl
                )
              }
              favoritePostsState.value = favoriteManager.getFavoritePosts()
            }, modifier = Modifier.align(Alignment.Start)
          ) {
            val icon = if (isFavorite) {
              Icons.Default.Favorite
            } else {
              Icons.Default.FavoriteBorder
            }
            Icon(icon, contentDescription = null)
          }
          //TODO ZROBIĆ Tagi.

          // Second Section Text
          Box(
            modifier = Modifier
              .weight(0.5f)
              .fillMaxWidth()
              .padding(start = 10.dp, end = 10.dp)
              .clip(RoundedCornerShape(5.dp))
              .background(Color.White),
            contentAlignment = Alignment.Center
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
  })
}



