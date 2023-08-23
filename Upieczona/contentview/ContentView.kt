package com.example.upieczona.contentview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.UpieczonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentViewUpieczona(
  postIndex: Int?, upieczonaViewModel: UpieczonaViewModel, navController: NavHostController
) {

  var mainPageState by remember {
    mutableStateOf(MainPageState.Default)
  }

  LaunchedEffect(mainPageState) {
    if (mainPageState == MainPageState.UpieczonaClicked) {
      navController.navigate(Destination.MainPageOfUpieczona.route)
    }
  }

  val postDetails = remember(upieczonaViewModel.allPosts) {
    postIndex?.let { index ->
      upieczonaViewModel.allPosts.value.find { it.id == index }
    }
  }

  Scaffold(
    topBar = {
      if (mainPageState == MainPageState.Default) {
        TopAppBarUpieczona(onUpieczonaClick = {
          mainPageState = MainPageState.UpieczonaClicked
        }, navController = navController)
      }
    },
    content = { padding ->
      Column(
        modifier = Modifier.padding(padding)
      ) {
        if (postDetails != null) {

          val urlsListPhotosUpieczona = remember(postDetails) {
            upieczonaViewModel.extractPhotosUrls(postDetails.content.rendered)
          }

          val ingredientTitleUpieczona = remember(postDetails) {
            upieczonaViewModel.getCachedIngredients(postDetails.content.rendered)
          }

          val decodedTextFromTitleUpieczona = remember(postDetails) {
            HtmlCompat.fromHtml(
              postDetails.title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
          }

          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
          ) {
            item {
              Divider()

              ImagePager(
                urlsListPhotosUpieczona.size,
                urlsListPhotosUpieczona
              )

              Column(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
              ) {
                Text(
                  modifier = Modifier.padding(5.dp),
                  fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
                  text = decodedTextFromTitleUpieczona,
                  fontSize = 20.sp,
                  textAlign = TextAlign.Center
                )
                Column(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                  horizontalAlignment = Alignment.Start,
                  verticalArrangement = Arrangement.Center
                ) {
                  FetchTitleWhenTwoTitle(ingredientTitleUpieczona,upieczonaViewModel,postDetails)
                }
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
              text = "Post not found",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            navController.navigateUp()
          }
        }
      }
    },
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(imageUrlsSize: Int, imageUrls: List<String>) {
  val pageState = rememberPagerState()
  HorizontalPager(
    pageCount = imageUrls.size,
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
  Divider()
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

