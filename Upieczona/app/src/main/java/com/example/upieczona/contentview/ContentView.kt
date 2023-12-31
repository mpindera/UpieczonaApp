package com.example.upieczona.contentview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.upieczona.destination.Destination
import com.example.upieczona.favorite.FavoriteManager
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.MaterialsUtils.decodeHtml
import com.example.upieczona.staticobjects.MaterialsUtils.swipeToReturn
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.UpieczonaMainViewModel
import com.example.upieczona.viewmodels.UpieczonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentViewUpieczona(
  postIndex: Int?,
  upieczonaViewModel: UpieczonaViewModel,
  navController: NavHostController,
  upieczonaMainViewModel: UpieczonaMainViewModel
) {
  val loc = LocalContext.current
  val favoriteManager = remember { FavoriteManager(loc) }
  val isSwipedRight by remember { mutableStateOf(false) }
  val postDetails = remember(upieczonaViewModel.allPosts) {
    postIndex?.let { index ->
      upieczonaViewModel.allPosts.value.find { it.id == index }
    }
  }
  val isFavorite = remember { mutableStateOf(favoriteManager.isPostFavorite(postIndex!!)) }

  val updateFavoriteState: () -> Unit = {
    isFavorite.value = favoriteManager.isPostFavorite(postIndex!!)
  }

  upieczonaMainViewModel.updatePageState(MainPageState.CONTENT_VIEW)
  Scaffold(
    topBar = {
      TopAppBarUpieczona(
        onUpieczonaClick = {
          navController.navigate(Destination.MainPageOfUpieczona.route)
        },
        onSearchIconClick = {},
        navController = navController,
        pageInfo = upieczonaMainViewModel.pageState.value,
        upieczonaMainViewModel = UpieczonaMainViewModel(),
      )
    },
  ) { padding ->

    Column(
      modifier = Modifier
        .padding(padding).swipeToReturn(isSwipedRight = isSwipedRight, navController = navController)
    ) {
      if (postDetails != null) {

        val urlsListPhotosUpieczona = remember(postDetails) {
          upieczonaMainViewModel.extractPhotosUrls(postDetails.content.rendered)
        }

        val ingredientTitleUpieczona = remember(postDetails) {
          upieczonaMainViewModel.getCachedIngredients(postDetails.content.rendered)
        }

        val decodedTextFromTitleUpieczona = remember(postDetails) {
          postDetails.title.rendered.decodeHtml()
        }

        LazyColumn(
          modifier = Modifier.fillMaxSize().padding(start = 13.dp, end = 13.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Top
        ) {
          item {
            ImagePager(
              urlsListPhotosUpieczona.size,
              urlsListPhotosUpieczona
            )
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
            ) {
              IconButton(
                onClick = {
                  if (isFavorite.value) {
                    favoriteManager.removeFavoritePost(postDetails.id)
                  } else {
                    favoriteManager.addFavoritePost(
                      postId = postDetails.id,
                      postName = decodedTextFromTitleUpieczona,
                      postImageUrl = urlsListPhotosUpieczona[0]
                    )
                  }
                  updateFavoriteState()
                },
              ) {
                val icon = if (isFavorite.value) {
                  Icons.Default.Favorite
                } else {
                  Icons.Default.FavoriteBorder
                }
                Icon(icon, contentDescription = null)
              }

              Text(
                modifier = Modifier.padding(top = 7.dp, bottom = 5.dp),
                fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
                text = decodedTextFromTitleUpieczona,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
              )

            }

            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
              horizontalAlignment = Alignment.Start,
              verticalArrangement = Arrangement.Center
            ) {
              FetchTitleWhenTwoTitle(
                ingredientTitleUpieczona,
                upieczonaMainViewModel,
                postDetails
              )

            }
          }
        }
      } else {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Post nie został znaleziony",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
          )
          navController.navigateUp()
        }
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(imageUrlsSize: Int, imageUrls: List<String>) {
  val pageState = rememberPagerState(pageCount = { imageUrls.size })
  HorizontalPager(
    state = pageState
  ) { pageIndex ->
    val imageUrl = imageUrls[pageIndex]
    Row(
      modifier = Modifier
        .height(400.dp)
        .width(400.dp)
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
  Row(
    modifier = Modifier
      .height(20.dp)
      .fillMaxSize(),
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.Center
  ) {
    repeat(imageUrlsSize) { iteration ->
      val color = if (pageState.currentPage == iteration) Color.DarkGray else Color.LightGray
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


